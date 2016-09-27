package WordCount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DatabaseController implements ActionListener {
	
	DatabaseSubmitView gui;
	WordCounterModel WCM;
	
	
	public DatabaseController(WordCounterModel WCM, DatabaseSubmitView gui) {
		super();
		this.gui = gui;
		this.WCM = WCM;
		


	}
	public DatabaseController() {
		super();
		this.WCM = null;
		this.gui = null;

	}
	

	public void actionPerformed(ActionEvent e) {
	String command = e.getActionCommand();
	
	//Where we submit our information to the DB
	
	if (command.equals("Submit Information")){
	
	} else if(command.equals("Clear Information")){
		
		gui.title.setText("");
		gui.authorFirst.setText("");
		gui.authorLast.setText("");
		gui.fictious.setSelectedIndex(0);
		gui.generes.setSelectedIndex(0);
		
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
		 * TODO: This is the current method to develop 
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
	
	
	/*
	 * This class makes sure our titles or authors names are
	 * not in excess of the limit we impose on them when initiating
	 * their fields.
	 */

	public class SizeFilter extends DocumentFilter {

	    private int maxCharacters;

	    public SizeFilter(int maxChars) {
	        maxCharacters = maxChars;
	    }

	    public void insertString(FilterBypass fb, int offs, String str, javax.swing.text.AttributeSet a)
	                    throws BadLocationException {

	        if ((fb.getDocument().getLength() + str.length()) <= maxCharacters) {
	            super.insertString(fb, offs, str, a);
	        }
	    }

	    public void replace(FilterBypass fb, int offs, int length, String str, javax.swing.text.AttributeSet a)
	                    throws BadLocationException {

	        if ((fb.getDocument().getLength() + str.length()
	                        - length) <= maxCharacters) {
	            super.replace(fb, offs, length, str, a);
	        } 
	    }
	    
	}

	public class listenIfCompleted implements DocumentListener {
		
		
		

		
		public listenIfCompleted(){
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			checkIfUpdated();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			checkIfUpdated();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			checkIfUpdated();
		}
		
		public void checkIfUpdated(){

			String title, authorFirst, authorLast, fictious, genere;
			title = String.valueOf(gui.title.getText());
			authorFirst = String.valueOf(gui.authorFirst.getText());
			authorLast = String.valueOf(gui.authorLast.getText());
			fictious = String.valueOf(gui.fictious.getSelectedItem());
			genere = String.valueOf(gui.generes.getSelectedItem());
				
			if(title != "title" && authorFirst != "author's first name" && authorLast != "author's last name" && fictious != " " && genere != " "){
				gui.submit.setEnabled(true);
			}
			if(title == "title" || title.isEmpty()  || authorFirst == "author's first name" || authorFirst.isEmpty() || authorLast == "author's last name" || authorLast.isEmpty() || fictious == " " || genere == " "){
				gui.submit.setEnabled(false);
			}
			
		
		}
		
	}
	public class comboListen implements ItemListener{

		/*
		 *This item listener is for our JCombobox listener. 
		 */
	
		
		
		comboListen(){

		}
		
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			String title, authorFirst, authorLast, fictious, genere;
			title = gui.title.getText();
			authorFirst = gui.authorFirst.getText();
			authorLast = gui.authorLast.getText();
			fictious = String.valueOf(gui.fictious.getSelectedItem());
			genere = String.valueOf(gui.generes.getSelectedItem());
				
			if(title != "title" && authorFirst != "author's first name" && authorLast != "author's last name" && fictious != " " && genere != " "){
				gui.submit.setEnabled(true);
			}
			if(title == "title" || title.isEmpty()  || authorFirst == "author's first name" || authorFirst.isEmpty() || authorLast == "author's last name" || authorLast.isEmpty() || fictious == " " || genere == " "){
				gui.submit.setEnabled(false);
			}
			
		}
		
		
	}
}
