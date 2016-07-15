package WordCount;

import java.awt.event.*;
import java.util.*;

public class WordCounterController implements ActionListener {

	WordCounterView gui;
	
	
	public WordCounterController(WordCounterView a){
		
		gui = a;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	String command = e.getActionCommand();
	if (command.equals("Submit")){
		String input = gui.input.getText();	
		zieph(input);
	}
	else if (command.equals("Clear")){
		gui.input.setText("");
		gui.output.setText("");
	}
	
		
	}
	
	 void zieph(String file){
		
		Scanner sc = new Scanner(file);
		HashMap<String, Integer> table = new HashMap<String, Integer>();
		int count = 0;
		int individualCount = 0;
		//loops through file to put words in associated array, value is the word usage 
		while (sc.hasNext()){
			String word = sc.next().toLowerCase(); 
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
		//remove all instance of punctuation
		table.remove("\\");
		table.remove(".");
		table.remove("?");
		table.remove(",");
		
		double uniquePercen = (double)individualCount / (double)count;
		//gui.input.setText("Total words: "+count+" Total Unique words: "+individualCount+ " Unique word percentage: %"+ uniquePercen*100 +" All the Unsorted words "+table);
		ValueComparator bvc = new ValueComparator(table);
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(bvc);
		sortedMap.putAll(table);
//		
//		gui.output.setText("The sorted words: " + sortedMap);
		
		
		String a = ("Total words: "+count+" \n Total Unique words: "+individualCount+ " \n Unique word percentage: %"+ uniquePercen *100+ " \n All the sorted words: \n"+sortedMap);
		gui.output.setText(a);
		gui.output.setCaretPosition(0);
		
		//		double ziephIdeal[] = new double [count];
//		double ziephLiteral[] = new double [count];
//		for (int i = 0; i <= count; i++){
//			ziephIdeal[i] = (double) 1 / (double) i;
//			//ziephLiteral[i] = (double) sortedMap.values().toArray()[0] / (double) sortedMap.values().toArray()[i]; 
//		}
//		System.out.println("Zieph Ideal: "+ ziephIdeal);
//		System.out.println("Zieph Lieteral: "+ ziephLiteral);
		
		
		
		
		}
	class ValueComparator implements Comparator<String>
	{
	    Map<String, Integer> base;
	
	    public ValueComparator(Map<String, Integer> base) {
	        this.base = base;
	    }
	
	    /*
	     * compares the values of of the Associative array
	     * to sort them.
	     * 
	     * (non-Javadoc)
	     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
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


