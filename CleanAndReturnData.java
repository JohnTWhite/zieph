package WordCount;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;


public class CleanAndReturnData {

	public CleanAndReturnData() {
		// TODO Auto-generated constructor stub
	}
	
public static WordCounterModel consolidate(WordCounterModel wcm){
		
		//Input WCM, what we will be comparing
		List<String> wordsIn = wcm.getWords();
		List<Integer> word_countIn = wcm.getWord_count();
		List<Integer> zipf_countIn = wcm.getZipf_word_count();
		//What will hold our output model parameters.
		List<String> wordsOut = new ArrayList<String>();
		List<Integer> word_countOut = new ArrayList<Integer>();
		List<Integer> zipf_countOut = new ArrayList<Integer>();
		
		int length_of_loop = wordsIn.size();
		
		/*
		 * Adds up all the words, ensures there are no repeated words.
		 * Whatever repeated words are counted in together. 
		 */
	
		int update_number = 0;
		int update_zipf_number = 0;
		
		for(int i = 0; i<length_of_loop; i++){
			
			String word = wordsIn.get(i);
			System.out.println("word var: "+word);
			int word_count = word_countIn.get(i);
			System.out.println("wordCoIn var: "+word_countIn.get(i));
			int zipf_count = zipf_countIn.get(i);
			System.out.println("zipfCoIn var: "+zipf_countIn.get(i));

			
			if(wordsOut.contains(word)){
				int word_index = wordsOut.indexOf(word);
				
				update_number = word_countOut.get(word_index);
				update_number += word_count;
				System.out.println("word var1: "+update_number);
				word_countOut.add(word_index, update_number);
				
				update_zipf_number = zipf_countOut.get(word_index);
				update_zipf_number += zipf_count;
				System.out.println("word var2: "+update_zipf_number);
				zipf_countOut.add(word_index, update_zipf_number);
				
			}else{
				wordsOut.add(word);
				word_countOut.add(word_count);
				zipf_countOut.add(zipf_count);
			}
			
		}
		

		wcm = new WordCounterModel(wordsOut, word_countOut,zipf_countOut);
		
		
		return wcm;
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
		word = word.replace("'", "");
		word = word.replace(")", "");
		word = word.replace("--", "");
		word = word.replace("(", "");
		word = word.replace("\"", "");
		word = word.replace("\\", "");
		if (word.length() > 25){
			word = "";
		}
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
	hashMap.remove("'");
	hashMap.remove("\"");
	hashMap.remove(")");
	hashMap.remove("(");
	hashMap.remove("--");
	
//	double uniquePercen = (double)individualCount / (double)count;
	//This object arranges our map in order of word frequency.
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
