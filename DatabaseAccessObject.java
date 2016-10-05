package WordCount;

import com.mysql.jdbc.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessObject {
	
	String userName = "ninjerman";
	String password = "Great4321!";
	String url = "jdbc:mysql://localhost:3306/Zipf";
	java.sql.Connection connection;
	ResultSet result_set;
	Statement statement;

	public DatabaseAccessObject() {
		
		
	}
	
	public DatabaseAccessObject(WordCounterModel WCM) {
		// TODO Auto-generated constructor stub
	}
	
	private java.sql.Connection getConnection(){
		
		
		try{
			System.out.println("intial strings intiated");
			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("connection connected");
			return connection;
			
		}catch(Exception e){
			System.out.println("This broke when creating connection: ");
			e.printStackTrace();
		}
		
		
		System.out.println("Connection Failure NULL Returned...");
		return null;
	}
	
	
	public String[] get_all(String retrieve){
		
		String results = "";
		
		String sql_statement = ""; 
		
		/*
		 * this if checks if we should proceed.
		 * Dependent upon which string was
		 * used to call our method is what 
		 * determines which SQL query is 
		 * appropriate.
		 *  
		 */
		if (retrieve == "titles"){
		sql_statement = "SELECT title FROM titles";
		}else if(retrieve == "authors"){
		sql_statement = "SELECT author_first, author_last FROM titles";
		}else if(retrieve == "generes"){
		sql_statement = "SELECT genere FROM titles";
		}else if(retrieve == "fiction"){
		sql_statement = "SELECT fiction FROM titles";
		}else{
			return null;
		}
		java.sql.Connection c = getConnection();
		try {
		statement = c.createStatement();
		System.out.println("statement made");
		statement.execute(sql_statement);
		System.out.println("statement executed");
		result_set = statement.getResultSet();
		
		while(result_set.next()){
			if(retrieve != "authors"){
			results +=  result_set.getString(1) + "/";
			}else{
			results +=  result_set.getString(1) + " " + result_set.getString(2)+ "/";
			}
		}
		c.close();
		System.out.println("retrieved result set and clossed connection");
		} catch (SQLException e) {
			System.out.println("Broke while creating/executing connection/statement: ");
			e.printStackTrace();
		}
		System.out.println("The results are :"+results);
		String[] result = results.split("/");

		return result;
	}
	
	public void submit_book(WordCounterModel WCM, DatabaseModel DBM ){
		
		String word_table_statement = "";
		String title_table_statement = "";
		
		List<String> words = WCM.getWords();
		List<Integer> word_count = WCM.getWord_count();
		List<Integer> zipf_count = WCM.getZipf_word_count();
		String title = DBM.getTitle();
		
		for(int i = 0, size = words.size(); i < size; i++){
			word_table_statement += "INSERT INTO word_count(word, count_literal, count_zipf,title) VALUES('"+
		words.get(i)+"','"+word_count.get(i)+"','"+zipf_count.get(i)+"','"+title+"');/";
		}
		
		title_table_statement += "INSERT INTO titles(title, author_first,author_last,genere,fiction) VALUES('"+
		title+"','"+DBM.getAuthorFirstName()+"','"+DBM.getAuthorLastname()+"','"+DBM.getGenere()+"','"+DBM.getFiction()+"');";
		
		try{
			java.sql.Connection c = getConnection();
			statement = c.createStatement();
			System.out.println("statement made");
			String [] sql = word_table_statement.split("/");
			for(int i = 0; i < sql.length; i++){
			statement.execute(sql[i]);
			}
			statement.execute(title_table_statement);
			c.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public WordCounterModel get_books_by(String type, String particular){
		
		List<String> wordList = new ArrayList<String>();
		List<Integer> wordCount = new ArrayList<Integer>();
		List<Integer> zipfCount = new ArrayList<Integer>();
		WordCounterModel WCM =null;
		String sql_statement;
		
		if (type == "titles"){
			sql_statement = "SELECT word_count.word, word_count.count_literal, word_count.count_zipf FROM word_count WHERE title ='"+particular+"'";
			}else if(type == "authors"){
				String[] parts = particular.split(" "); 
			sql_statement = "SELECT word_count.word, word_count.count_literal, word_count.count_zipf FROM word_count INNER JOIN titles ON word_count.title = titles.title "
					+ "WHERE titles.author_first="+parts[0]+" AND titles.author_last="+parts[1];
			}else if(type == "generes"){
			sql_statement = "SELECT word_count.word, word_count.count_literal, word_count.count_zipf FROM word_count INNER JOIN titles ON word_count.title = titles.title"
					+ "WHERE titles.genere="+particular;
			}else if(type == "fiction"){
			sql_statement = "SELECT word_count.word, word_count.count_literal, word_count.count_zipf FROM word_count INNER JOIN titles ON word_count.title = titles.title"
					+ "WHERE titles.fiction="+particular;
			}else{
				return null;
			}
		
		try{
			java.sql.Connection c = getConnection();
			statement = c.createStatement();
			System.out.println("statement made");
			result_set = statement.executeQuery(sql_statement);
			//result_set = statement.getResultSet();
			System.out.println("Result set made");
			//TODO: Turn result set in WCM, return that B, finish this B.
			while(result_set.next()){
			String result_set_string = result_set.getString(1)+ result_set.getString(2)+ result_set.getString(3);
			System.out.println(result_set_string);}
			
			c.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return WCM;
		
	}
}
