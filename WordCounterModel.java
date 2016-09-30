package WordCount;

import java.util.ArrayList;
import java.util.List;

public class WordCounterModel {
	
	private List<String> words = new ArrayList<String>();
	private List<Integer> word_count = new ArrayList<Integer>();
	private List<Integer> zipf_word_count = new ArrayList<Integer>();
	
	
	public WordCounterModel(List<String> words, List<Integer> word_count, List<Integer> zipf_word_count) {
		super();
		this.words = words;
		this.word_count = word_count;
		this.zipf_word_count = zipf_word_count;
	}




	public List<String> getWords() {
		return words;
	}



	public List<Integer> getWord_count() {
		return word_count;
	}


	public List<Integer> getZipf_word_count() {
		return zipf_word_count;
	}
	
	public int getLength(){
		return words.size();
	}




}
