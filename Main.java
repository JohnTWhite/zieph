package WordCount;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main (String[] args){
		
		
		//WordCounterView x = new WordCounterView();
		
		
		
		
		//TODO: delete all this nonsense. 
		DatabaseAccessObject DAO = new DatabaseAccessObject();
		
		WordCounterModel wcm = DAO.get_books_by("titles", "Animal Farm");
		//Error checking
		String x = "";
		List<String>y = wcm.getWords();
		List<Integer> z = wcm.getWord_count();
		for(int i = 0; i<50; i++){
			System.out.println(y.get(i)+ ": :"+z.get(i));
		}

		WordCounterModel wcm2 = consolidate(wcm);
		x = "";
		//y = wcm2.getWords();
		z = wcm2.getWord_count();
		for(int i = 0; i<50; i++){
		//	x += y.get(i)+ ": :";
		
			System.out.println(y.get(i)+ ": :"+z.get(i));
		}

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
		
		int length_of_first_loop = wordsIn.size();
		
		/*
		 * Adds up all the words, ensures there are no repeated words.
		 * Whatever repeated words are counted in together. 
		 */
		System.out.println("Second Set");	
		for(int i = 0; i<length_of_first_loop; i++){
			
			String word = wordsIn.get(i);
			int word_count = word_countIn.get(i);
			int zipf_count = zipf_countIn.get(i);
			
			if(wordsOut.contains(word)){
				int word_index = wordsOut.indexOf(word);
				
				int update_number = word_countOut.get(word_index);
				update_number += word_count;
				word_countOut.add(word_index, update_number);
				
				int update_zipf_number = zipf_countOut.get(word_index);
				update_zipf_number += zipf_count;
				word_countOut.add(word_index, update_zipf_number);
				
			}else{
				wordsOut.add(word);
				word_countOut.add(word_count);
				zipf_countOut.add(zipf_count);
			}
			
		}
		
		/*
		 * This loop will sort our List's with bubble sort. 
		 * The upper bound (n²)of bubble sort should not be 
		 * close to reached by this, because the list's
		 * will almost be sorted entirely when we receive it.
		 */
		
		
		int length_for_second_loop = word_countOut.size() - 1;
		int swaps = 0;
		
		
//		do{
//			swaps = 0;
//			System.out.println("This is the second loop: ");	
//			for(int i = 0; i <length_for_second_loop;i++){
//				if(word_countOut.get(i)<word_countOut.get(i+1)){
//					
//					int place_holder_count = word_countOut.get(i);
//					word_countOut.add(i, word_countOut.get(i+1));
//					word_countOut.add(i+1, place_holder_count);
//					
//					int place_holder_z_count = zipf_countOut.get(i);
//					zipf_countOut.add(i, zipf_countOut.get(i+1));
//					zipf_countOut.add(i+1, place_holder_z_count);
//					
//					String place_holder_word = wordsOut.get(i);
//					wordsOut.add(i, wordsOut.get(i+1));
//					wordsOut.add(i+1, place_holder_word);
//					
//					swaps += 1; 
//					System.out.println(swaps);	
//				}
//				
//			}
//		}while(swaps != 0);
		System.out.println("This is the loop ending");	
		wcm = new WordCounterModel(wordsOut, word_countOut,zipf_countOut);
		
		
		return wcm;
	}
	
}
