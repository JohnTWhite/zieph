package WordCount;

import java.awt.event.*;
import java.lang.Math.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.JFileChooser;

public class WordCounterController implements ActionListener{

	//WordCounter view object 
	
	WordCounterView gui;
	DatabaseAccessObject DAO;
	
	/*
	 * I make a lot of jokes, some
	 * people say I'm a ...
	 */
	CleanAndReturnData CARD = new CleanAndReturnData();; 
	
	public WordCounterController(WordCounterView a){
		super();
		gui = a;
		
	}
	
	//Action handlers
	
	@Override
	public void actionPerformed(ActionEvent e) {
	String command = e.getActionCommand();
	if (command.equals("Submit")){
		if(gui.stringGraph.isEnabled() != true){
		String input = gui.input.getText();	
		
		//Create our model with our Zieph method. 
		WordCounterModel z = CARD.zieph(input);
		int length = z.getLength();
		List<String> words = z.getWords();
		List<Integer> word_count = z.getWord_count();
		List<Integer> zipf_word_count = z.getZipf_word_count();
		
		//Count will be the total amount of words used in a book
		int count = 0;
		
		for(int i = 0; i < length; i++){
			
			count += word_count.get(i);
			
			
		}

		gui.dtm.addRow(new Object[] {"Total Words", count});
		gui.dtm.addRow(new Object[] {"Unique Words", length});
		double uniquePercen = (double) length / count;
		uniquePercen *= 100;
		gui.dtm.addRow(new Object[] {"Unique Words Percentage ", "%"+uniquePercen});
		
		for(int i = 0; i < length; i++){
			
			gui.dtm.addRow(new Object[] {words.get(i),word_count.get(i), zipf_word_count.get(i) });
			
		}
		gui.stringGraph.setEnabled(true);
		gui.stringDB.setEnabled(true);};
		

		
	}
	else if (command.equals("Clear")){
		gui.input.setText("");
		gui.stringGraph.setEnabled(false);
		gui.fileGraph.setEnabled(false);
		gui.stringDB.setEnabled(false);
		
		int count = gui.dtm.getRowCount();
		for (int i = count -1 ; i >= 0 ; i--){
			gui.dtm.removeRow(i);
		}
	}
	else if (command.equals("Main Menu")){
		gui.cardLayout.show(gui.cardHolder, "Menu");
		
	}
	else if (command.equals("Type your text")){
		gui.cardLayout.show(gui.cardHolder, "String Input");
		
	}
	else if (command.equals("Find a file")){
		gui.cardLayout.show(gui.cardHolder, "File Input");
		
	}
	else if (command.equals("Database")){
		gui.cardLayout.show(gui.cardHolder, "View Database");
		
	}
	else if (command.equals("Clear File")){

		int count = gui.file_dtm.getRowCount();
		for (int i = count -1 ; i >= 0 ; i--){
			gui.file_dtm.removeRow(i);
		}
		gui.fileGraph.setEnabled(false);
		gui.fileDB.setEnabled(false);
	}
	else if (command.equals("Find File")){
		
		
		//the file chooser object goes here. We use it to get the text file to read in from the user.
		
		int returnVal = gui.fileChooser.showOpenDialog(gui);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File bookFile = gui.fileChooser.getSelectedFile();

          //Create our model with our Zieph method. 
    		WordCounterModel z = CARD.zieph(bookFile);
    		int length = z.getLength();
    		List<String> words = z.getWords();
    		List<Integer> word_count = z.getWord_count();
    		List<Integer> zipf_word_count = z.getZipf_word_count();
    		
    		//Count will be the total amount of words used in a book
    		int count = 0;
    		
    		for(int i = 0; i < length; i++){
    			
    			count += word_count.get(i);
    			
    			
    		}

    		gui.file_dtm.addRow(new Object[] {"Total Words", count});
    		gui.file_dtm.addRow(new Object[] {"Unique Words", length});
    		double uniquePercen = (double) length / count;
    		uniquePercen *= 100;
    		gui.file_dtm.addRow(new Object[] {"Unique Words Percentage ", "%"+uniquePercen});
    		
    		for(int i = 0; i < length; i++){
    			
    			gui.file_dtm.addRow(new Object[] {words.get(i),word_count.get(i), zipf_word_count.get(i) });
    			
    		}
    		};
		gui.fileGraph.setEnabled(true);
		gui.fileDB.setEnabled(true);
	}
	else if (command.equals("Graph String")){
		DrawGraph m = new DrawGraph(gui.dtm);
	}
	else if (command.equals("Graph File")){
		DrawGraph m = new DrawGraph(gui.file_dtm);
	}
	else if (command.equals("Graph Results")){
		DrawGraph m = new DrawGraph(gui.output_dtm);
	}
	else if (command.equals("Commit type to DB")){
		DatabaseSubmitView m = new DatabaseSubmitView(CARD.zieph(gui.input.getText()));
	}
	else if (command.equals("Commit file to DB")){
		
		List<String> words = new ArrayList<String>();
		List<Integer> word_count = new ArrayList<Integer>();
		List<Integer> zipf_count = new ArrayList<Integer>();
		
		int length = gui.file_dtm.getRowCount();
		//TODO; FIX THIS NONSENSE!!
		for(int i = 0; i<length;i++){
			
			if(i>2){
			words.add(gui.file_dtm.getValueAt(i, 0).toString());
			word_count.add(Integer.parseInt(gui.file_dtm.getValueAt(i, 1).toString()));
			zipf_count.add(Integer.parseInt(gui.file_dtm.getValueAt(i, 2).toString()));
			
		}}
			System.out.println(word_count.get(0));
		
		WordCounterModel model = new WordCounterModel(words,word_count,zipf_count);
		
		DatabaseSubmitView fileSubmitView = new DatabaseSubmitView(model);
	}
	else if (command.equals("retrieve from database")){
		
		//clear table before we set it. 
		int countClear = gui.output_dtm.getRowCount();
		for (int i = countClear -1 ; i >= 0 ; i--){
			gui.output_dtm.removeRow(i);
		}
		
		//our dao used to retrieve or wcm from db
		WordCounterModel wcm = DAO.get_books_by(gui.fields.getSelectedItem().toString(), gui.allTitles.getSelectedItem().toString());
		int length = wcm.getLength();
		List<String> words = wcm.getWords();
		System.out.println(words.get(0).toString());
		List<Integer> word_count = wcm.getWord_count();
		List<Integer> zipf_word_count = wcm.getZipf_word_count();
		
		//Count will be the total amount of words used in a book
		int count = 0;
		
		for(int i = 0; i < length; i++){
			
			count += word_count.get(i);
			
			
		}

		gui.output_dtm.addRow(new Object[] {"Total Words", count});
		gui.output_dtm.addRow(new Object[] {"Unique Words", length});
		double uniquePercen = (double) length / count;
		uniquePercen *= 100;
		gui.output_dtm.addRow(new Object[] {"Unique Words Percentage ", "%"+uniquePercen});
		
		for(int i = 0; i < length; i++){
			
			gui.output_dtm.addRow(new Object[] {words.get(i),word_count.get(i), zipf_word_count.get(i) });
			
		}
		gui.dbGraph.setEnabled(true);
		
		}
		else if (command.equals("Clear table")){

			int count = gui.output_dtm.getRowCount();
			for (int i = count -1 ; i >= 0 ; i--){
				gui.output_dtm.removeRow(i);
			}
			gui.dbGraph.setEnabled(false);
		
		
		
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
	
		String selection = gui.fields.toString();
		
		gui.allTitles.removeAllItems();
		gui.allTitles.setVisible(true);
		
		
			
			DAO = new DatabaseAccessObject();
			String[] comboContent = DAO.get_all(gui.fields.getSelectedItem().toString());
			
			gui.allTitles.setEnabled(true);
			
			if(gui.fields.getSelectedItem().toString() != ""){
				for(int i = 0; i < comboContent.length; i++)
			
					gui.allTitles.addItem(comboContent[i]);
					
					
			}
					
		}
	}
}


