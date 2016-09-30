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
		WordCounterModel z = zieph(input);
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
	}
	else if (command.equals("Find File")){
		
		
		//the file chooser object goes here. We use it to get the text file to read in from the user.
		
		int returnVal = gui.fileChooser.showOpenDialog(gui);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File bookFile = gui.fileChooser.getSelectedFile();

          //Create our model with our Zieph method. 
    		WordCounterModel z = zieph(bookFile);
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
	}
	else if (command.equals("Graph String")){
		DrawGraph m = new DrawGraph(gui.dtm);
	}
	else if (command.equals("Graph File")){
		DrawGraph m = new DrawGraph(gui.file_dtm);
	}
	else if (command.equals("Commit to DB")){
		//DatabaseSubmitView m = new DatabaseSubmitView();
	}
	
	//TODO: delete this when done.
	else if (command.equals("TRIAL")){
		
		WordCounterModel wcm = zieph("this is a test, by and by a test test testy test is what this is for you guys out there");
		
		DatabaseSubmitView m = new DatabaseSubmitView(wcm);
		
		
		
	}
	}


	
	/*
	 * The Zieph functions take in either a string or file and 
	 * then return our model object which contains a list of words
	 * used in the input arranged by frequency beginning with the most
	 * used word (presumably the word "the" if in English).
	 * 
	 * The model object also contains a list of how often the words 
	 * are used, and a list of how often the Zipf law predicts the
	 * words use. 
	 * 
	 */
		
	WordCounterModel zieph(File file){
		
		Scanner sc;
		String all_words = "";
		
		try {
			
			sc = new Scanner(file);

        while(sc.hasNext()){
        	
        	all_words += sc.next().toLowerCase()+" "; 
        	
		
		} 
        
		
		
		} catch (FileNotFoundException e) {
			// File not there.
			all_words= "Error";
			System.out.println("Did not find file");
			e.printStackTrace();
		} catch (IOException e) {
			// In out error
			all_words= "Error";
			System.out.println("Did not properly read file in.");
			e.printStackTrace();
		}
		//Use our existing function to populate the model object. 
		return zieph(all_words);
	} 
	
    WordCounterModel zieph(String string){
		
		Scanner sc = new Scanner(string);
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		int count = 0;
		int individualCount = 0;
		//loops through file to put words in associated array, value is the word usage 
		while (sc.hasNext()){
			String word = sc.next().toLowerCase(); 
			//Remove punctuation from the string
			word = word.replace(".", "");
			word = word.replace("!", "");
			word = word.replace("?", "");
			word = word.replace(",", "");
			if(hashMap.containsKey(word))
			{
				hashMap.put(word, hashMap.get(word)+1);
			}
			else
			{
				hashMap.put(word, 1);
				individualCount++;
			}

			count++;
		}
		sc.close();
		//remove all instance of punctuation as individual entries in tree map
		//(an example being if there was a space between the word and punctuation)
		hashMap.remove("\\");
		hashMap.remove(".");
		hashMap.remove("?");
		hashMap.remove(",");
		
		double uniquePercen = (double)individualCount / (double)count;
		ValueComparator bvc = new ValueComparator(hashMap);
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(bvc);
		sortedMap.putAll(hashMap);
		
		
		double runningTotal = 1;
		int mostUsedWord = 0;
		
		//Implement the array list's that will compose our model.
		List<String> words = new ArrayList<String>();
		List<Integer> word_count = new ArrayList<Integer>();
		List<Integer> zipf_word_count = new ArrayList<Integer>();

		for (Entry<String, Integer> entry : sortedMap.entrySet()){

		if (runningTotal == 1){
			mostUsedWord = entry.getValue();
		}
		
		//Calculates the amount of times the word is expected to be used
		//according to the ziefValue. The number assumes it is used at 
		//least once.
		double ziefValueDouble = (1.0/runningTotal) * mostUsedWord;
		int ziefValue = (int)(ziefValueDouble);
			if (ziefValue == 0){ziefValue= 1;}

		
		//Add to our lists that will create our model.
		words.add(entry.getKey());
		word_count.add(entry.getValue());
		zipf_word_count.add(ziefValue);
		
		runningTotal++;

		}
		
		//Create a model object to return it.
		
		WordCounterModel wcm = new WordCounterModel(words,word_count,zipf_word_count);
	
		return wcm;
		
		
		}
	class ValueComparator implements Comparator<String>
	{
	    Map<String, Integer> base;
	
	    public ValueComparator(Map<String, Integer> base) {
	        this.base = base;
	    }
	
	    /*
	     * compares the values of of the hash map table
	     * to sort them.
	     * 
	     */
	    public int compare(String a, String b) {
	        if (base.get(a) >= base.get(b)) {
	            return -1;
	        } else {
	            return 1;
	        }
	    }
	}
	
	
}


