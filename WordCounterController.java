package WordCount;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JFileChooser;

public class WordCounterController implements ActionListener {

	//WordCounter view object as our visual interface. 
	
	WordCounterView gui;
	
	public WordCounterController(WordCounterView a){
		
		gui = a;
		
	}
	
	//Action handlers
	
	@Override
	public void actionPerformed(ActionEvent e) {
	String command = e.getActionCommand();
	if (command.equals("Submit")){
		if(gui.stringGraph.isEnabled() != true){
		String input = gui.input.getText();	
		zieph(input);
		gui.stringGraph.setEnabled(true);}
		
	}
	else if (command.equals("Clear")){
		gui.input.setText("");
		gui.stringGraph.setEnabled(false);
		
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
		gui.fileGraph.setEnabled(true);
		
		
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
            
            zieph(bookFile);
		
		
		}
	}
	else if (command.equals("Graph String")){
		DrawGraph m = new DrawGraph(gui.dtm);
	}
	else if (command.equals("Graph File")){
		DrawGraph m = new DrawGraph(gui.file_dtm);
	}
	}


	
	//Zipf function for both file and string input. 
		
	void zieph(File file){
		
		Scanner sc;
		
		
		try {
			
			sc = new Scanner(file);

		
		HashMap<String, Integer> table = new HashMap<String, Integer>();
		int count = 0;
		int individualCount = 0;
		//loops through file to put words in associated array, value is the word usage 
        while(sc.hasNext()){
        	
        	String word = sc.next();
				
				word = word.toLowerCase(); 
				
				//Remove punctuation from the string
				word = word.replace(".", "");
				word = word.replace("!", "");
				word = word.replace("?", "");
				word = word.replace(",", "");
				
			 
				if(table.containsKey(word))
				{
					table.put(word, table.get(word)+1);
				}
				else
				{
					table.put(word, 1);
					individualCount++;
				}
				count++;
				}
		sc.close();
		//remove all instance of punctuation as individual entries in tree map
		//(an example being if there was a space between the word and punctuation)
		table.remove("\\");
		table.remove(".");
		table.remove("?");
		table.remove(",");
		
		double uniquePercen = (double)individualCount / (double)count;
		ValueComparator bvc = new ValueComparator(table);
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(bvc);
		sortedMap.putAll(table);

		
		
		gui.file_dtm.addRow(new Object[] {"Total Words", count});
		gui.file_dtm.addRow(new Object[] {"Unique Words", individualCount});
		gui.file_dtm.addRow(new Object[] {"Unique Words Percentage", uniquePercen});
		
		double runningTotal = 1;
		int longestWordValue = 0;
		
		for (Entry<String, Integer> entry : sortedMap.entrySet()){
			
		if (runningTotal == 1) {
			longestWordValue = entry.getValue();
		}
			
		double ziefDouble = (1 / runningTotal)*longestWordValue;
		int ziefValue = (int) (ziefDouble);
		if(ziefValue == 0){ziefValue = 1;}
		
		gui.file_dtm.addRow(new Object[] {entry.getKey(), entry.getValue(), ziefValue});
		
		runningTotal++;
		
		} 
		
		
		} catch (FileNotFoundException e) {
			// Could not read the file
			gui.file_dtm.addRow(new Object[] {"Did not properly find file", -1});
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			gui.file_dtm.addRow(new Object[] {"Did not properly read file", -2});
			e.printStackTrace();
		}
		
		
	} 
	
    void zieph(String string){
		
		Scanner sc = new Scanner(string);
		HashMap<String, Integer> table = new HashMap<String, Integer>();
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
			if(table.containsKey(word))
			{
				table.put(word, table.get(word)+1);
			}
			else
			{
				table.put(word, 1);
				individualCount++;
			}

			count++;
		}
		sc.close();
		//remove all instance of punctuation as individual entries in tree map
		//(an example being if there was a space between the word and punctuation)
		table.remove("\\");
		table.remove(".");
		table.remove("?");
		table.remove(",");
		
		double uniquePercen = (double)individualCount / (double)count;
		ValueComparator bvc = new ValueComparator(table);
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(bvc);
		sortedMap.putAll(table);
		
		gui.dtm.addRow(new Object[] {"Total Words", count});
		gui.dtm.addRow(new Object[] {"Unique Words", individualCount});
		gui.dtm.addRow(new Object[] {"Unique Words Percentage", uniquePercen});
		
		double runningTotal = 1;
		int mostUsedWord = 0;
		
		//int zf =  sortedMap.get("the");

		for (Entry<String, Integer> entry : sortedMap.entrySet()){

		if (runningTotal == 1){
			mostUsedWord = entry.getValue();
		}
		

		double ziefValueDouble = (1.0/runningTotal) * mostUsedWord;
		int ziefValue = (int)(ziefValueDouble);
			if (ziefValue == 0){ziefValue= 1;}
		gui.dtm.addRow(new Object[] {entry.getKey(), entry.getValue(), ziefValue});
		
		runningTotal++;

		}
		
		
		
		}
	class ValueComparator implements Comparator<String>
	{
	    Map<String, Integer> base;
	
	    public ValueComparator(Map<String, Integer> base) {
	        this.base = base;
	    }
	
	    /*
	     * compares the values of of the hashmap table
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


