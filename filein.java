package FileTrial;

import java.io.*;
import java.util.*;

//TODO: Create Zieph class that implements Zieph correlation, and array combination.
public class filein{

	/*
	 * properties
	 * 
	 * The book - file -What we're working with
	 * sorted list - All the words arranged in order of usage
	 * unique words - The amount of times a new word is created
	 * overall words - The total amount of words in a file/book
	 * 
	 */
	
	private File book;
	private TreeMap<String, Integer> sortedList;
	private int uniqueWords;
	private int overallWords;
	
	
	
	
	public filein(File book) throws IOException
	{
		
		this.book = book;
		this.sortedList = this.ReadTheBook(book);
	
		/*
		 * The other properties are set in the method
		 * ReadThisBook
		 * 
		 */
	}
	
/*
 * methods
 * 
 * return sortedList
 * return unique word percentage
 * return ziephen diviation
 * 
 * 
 * 
 * 
 * 
 * 
 */
	
	
		public TreeMap<String, Integer> getList()
		{
			return this.sortedList;
			
		}	
		public double uniqueWordsPercentage()
		{
			
			return this.uniqueWords / this.overallWords;
		}
		public int wordSearch(String wor)
		{
			int x = this.sortedList.get(wor);
			return x;
		}
		public File getBook(){
			return this.book;
		}
	    

		
	
	
	
	
 /* 
 * 
 * 
 * This function takes in a text file and reads the contents
 * of that file into HashMap. An if else condition that logs each word as
 *  the key to this HashMap, and the use of that word is the value. 
 *  
 * I have a class within this method that implements Comparator
 * which is a helpful interface to have with the previous
 * tree map of words. This comparator allows me to easily sort 
 * 
 * 
 * 
 */
	

public TreeMap<String, Integer> ReadTheBook(File file) throws IOException{
	
	//Bring in the file into a scanner
	this.book = file;
	
	
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
	//TODO, remove all things not letters
	table.remove("\\");
	this.overallWords = count;
	this.uniqueWords = individualCount;
	double uniquePercen = (double)individualCount / (double)count;
	System.out.println("Total words: "+count+" Total Unique words: "+individualCount+ " Unique word percentage: %"+ uniquePercen*100 +" All the Unsorted words "+table);
	ValueComparator bvc = new ValueComparator(table);
	TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(bvc);
	sortedMap.putAll(table);
	System.out.println("Total words "+count+" Total Unique words "+individualCount+ " Unique word percentage: %"+ uniquePercen *100+ " All the sorted words "+sortedMap);
//	double ziephIdeal[] = new double [count];
//	double ziephLiteral[] = new double [count];
//	for (int i = 0; i <= count; i++){
//		ziephIdeal[i] = (double) 1 / (double) i;
//		//ziephLiteral[i] = (double) sortedMap.values().toArray()[0] / (double) sortedMap.values().toArray()[i]; 
//	}
//	System.out.println("Zieph Ideal: "+ ziephIdeal);
//	System.out.println("Zieph Lieteral: "+ ziephLiteral);
	
	
	this.sortedList = sortedMap;
	return sortedMap;
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

	public static void main(String[] args) throws IOException
		{
			
			//filein y = new filein();
			File MacBeth = new File("NB.txt");
			File Bible = new File("NB2.txt");
			//File animalFarm = new File("MacBeth");
			filein x = new filein(Bible);
//			x.ReadTheBook(animalFarm);
			x.ReadTheBook(Bible);
			
			
			//x.wordSearch("the");
			
			//y.ReadTheBook(Bible, "");
			
		}
}
		
		

