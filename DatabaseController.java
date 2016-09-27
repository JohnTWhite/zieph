package WordCount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatabaseController implements ActionListener {
	
	DatabaseSubmitView gui;
	WordCounterModel WCM;
	
	public DatabaseController(WordCounterModel WCM) {
		super();
		this.gui = gui;
		this.WCM = WCM;

	}
	public DatabaseController() {
		super();
		this.gui = gui;
		this.WCM = null;

	}
	

	public void actionPerformed(ActionEvent e) {
	String command = e.getActionCommand();
	if (command.equals("Submit Information")){
	
	}
	}
	
	//Connection creation, returns a connection to our Azure DB.
	
	 private static Connection getConnection() throws Exception {
	    	
	    	try{
	    		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	    		String url =  "jdbc:sqlserver://zifp.database.windows.net:1433;database=zifp;user=zifp@zifp;password=WordCount!123!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	    		System.out.println("Connection strings successful");
	    		Class.forName(driver);
	    		System.out.println("Class Driver successful");
	    		Connection con = DriverManager.getConnection(url);
	    		System.out.println("Connection successful");
	    		
	    		return con;
	    		
	    	}catch(Exception e){
	    		
	    		System.out.println("Did not work: "+e);
	    		
	    	}
	    	
	    	return null;
	    	
	    }

		
		//this function returns all the title names in our database
	 
	String[] allTitles(){
			
	        Statement statement = null;   
	        ResultSet resultSet = null;
	        Connection connection = null;  
	        PreparedStatement prepsInsertProduct = null;
	        System.out.println("Connection resources created");
	        
	        String titleString = "All/";
	                      
	        try {  
	            
	            
	            connection = getConnection();  
	            System.out.println("Connection created");
	            
	            statement = connection.createStatement();
	            System.out.println("Statment created");
	            
	            String getTableNames = "SELECT * FROM information_schema.tables WHERE TABLE_TYPE='BASE TABLE'";
	            
	            resultSet = statement.executeQuery(getTableNames);
	            
	            System.out.println("Table results recieved");
	            
	         
	            while(resultSet.next()){

	            	titleString +=  resultSet.getString(3) + "/";
	            	
	            }
	            connection.close();
	            
	            
	     
	         }
	        catch (Exception e) {  
	            System.out.println("Didn't work: " + e);
	            }  
			
			
			String[] returnArray = titleString.split("/");
			
			return returnArray;
		}

		/*
		 * 
		 * This function will generate a DatabaseSubmitView object.
		 * That object will collect the title of the book, the 
		 * author of the books first and last name, the genre of the 
		 * book (romance, drama, comedy, etc), and the 'type' of 
		 * work the book is (fiction, non-fiction exclusively).
		 * Only when these fields are satisfied will the user
		 * be able to submit to the DB. 
		 * 
		 */
		
	void submit_book(WordCounterModel input){

		WordCounterModel WCM = input;
			
			
	}
}
