package WordCount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	DatabaseAccessObject DAO;
	
	
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
	
	/*
	 * Where we submit our information to the DB.
	 * We use a DatabaseAccessObject to hold any 
	 * connections to the database. 
	 * 
	 */
	
	
	if (command.equals("Submit Information")){
		
		DatabaseModel DBM = new DatabaseModel(gui.title.getText(), gui.authorFirst.getText(),
				gui.authorLast.getText(),gui.fictious.getSelectedItem().toString(),gui.generes.getSelectedItem().toString());
		
		
		
		DAO = new DatabaseAccessObject();
		if(!DAO.check_title_already(gui.title.getText())){
		DAO.submit_book(WCM, DBM);}else{
			JFrame frame = new JFrame("Oops");
			JOptionPane.showMessageDialog(frame,
				    "Already a book in the database.","Title exists",JOptionPane.WARNING_MESSAGE);
		}
		gui.dispose();
	
	} else if(command.equals("Clear Information")){
		
		gui.title.setText("");
		gui.authorFirst.setText("");
		gui.authorLast.setText("");
		gui.fictious.setSelectedIndex(0);
		gui.generes.setSelectedIndex(0);
		
	}
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
	//checks if database submit view text fields are completed
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
	//checks if database submit view combo boxes are selected
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
