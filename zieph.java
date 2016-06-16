package bookWordCount;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import banking.account;

/**
 * 
 * This program is made to demonstrate (or 
 * disprove) a mathematical principle proposed
 * by a man named Zieph. It states that humans
 * will subconsciously favor words when writing.
 * So much so that it can be demonstrated 
 * mathematically.
 * 
 * The most often occurring word is used twice as
 * often as the second, and that three times
 * as often as the third. Continuing to
 * the point that all remaining words are used once.
 * 
 * The rank of a word determines how often it's
 * used.
 *  r = rank; 
 * 1/r; - can plot it's frequency of use compared
 * 		  to the the next lowest ranking word. 
 * 
 * In this method I plan on loading a local 
 * file containing a book. I will create
 * a loop that checks if all the contents of the
 * file have been read into an array that will
 * hold the word as an index and it's use as the 
 * value.
 * 
 * I will sort that array with bubble sort.
 * I will calculate frequency of use for each
 * word.
 * I will have that frequency output as a fraction 
 * and
 * compare it to the Zieph curve.
 * 
 * 
 * @author Captain
 *
 */

public class zieph {
	
	/*
	 * has
	 * 
	 * array list to hold the words and their use
	 * file name
	 * 
	 * 
	 */
	private List<String> books;
	private String fileName;
	
	//Constructor
	public zieph(String file){
		this.fileName = file;
		
		
	}

	/*
	 * execute
	 * loadfile
	 * bubblesort
	 * 
	 */
	/**
	 * This will go through all the
	 * content counting and using bubblesort
	 * at the end
	 */
	public void execute(String file)
	{
		int cs ;
		int List;
		int String;
		Object file1;
		this.books = List<String> readAllLines(file1, cs);
	}
	/**
	 * This will load the file
	 * that holds the book
	 */
	public void loadFile()
	{
		
	}
	/**
	 * This will sort our array
	 * in rank and use.
	 */
	public void bubbleSort()
	{
		
	}
}
