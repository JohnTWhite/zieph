package WordCount;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;


public class WordCounterView extends JFrame {

	
//Bring in our controller. Which holds our action listener events.
	
	WordCounterController controller = new WordCounterController(this);
	
//Our JPanel to hold all the different JPanels using CardLayout.
	
	JPanel cardHolder = new JPanel();
	
	//Our layout will be CardLayout, so that we can change panels in cardHolder.
	
	CardLayout cardLayout = new CardLayout();
	
//Main menu panel with accompanying resources. This is the initial visible panel.
	JPanel menuPanel = new JPanel();
	
		
		JButton stringInput = new JButton("Type your text");
		JButton fileInput = new JButton("Find a file");
		JButton databaseInput = new JButton("Database");
	

//	This panel is for the string input UI elements. 
	
	JPanel stringPanel = new JPanel();
	
	//these elements are used in the String Card
	
		JTextArea input = new JTextArea(20,20);
		JTable output = new JTable(20,20);
		JTable fileTableOutput = new JTable(20,20);
		JButton stringGraph = new JButton("Graph String");
		JButton fileGraph = new JButton("Graph File");
		
		//These model's will render the output of the count of words generated in the String Card
		
		DefaultTableModel dtm = new DefaultTableModel(new String[] { "Words", "Count", "Zipf estimate"},0);
		DefaultTableModel file_dtm = new DefaultTableModel(new String[] { "Words", "Count", "Zipf estimate"},0);
		
//	This panel is for the file input UI elements.
		
	JPanel filePanel = new JPanel();
	
		JFileChooser fileChooser = new JFileChooser();
		
// This panel is for the user to be able to view the database.
	
	JPanel databasePanel = new JPanel();
	
	
	public WordCounterView(){
		
		super("Word Counter");
		setSize(550,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	

		cardHolder.setLayout(cardLayout);
		

		// These functions will setup our individual views (cards) in our card controller.
	
		setupMenuCard();
		
		setupStringCard();
		
		setupFileCard();
		
		setupDatabaseCard();
		
		
		cardHolder.add(menuPanel, "Menu");
		cardHolder.add(stringPanel, "String Input");
		cardHolder.add(filePanel, "File Input");
		cardHolder.add(databasePanel, "View Database");
		
		cardLayout.show(cardHolder, "Menu");

		//This adds our finished panel.
		add(cardHolder);
		
		setVisible(true);
		
		
	
		
	}
	
	private void setupMenuCard(){
		
		
		JLabel menuOverview = new JLabel("<html>Decide between loading text directly into our database, "
				+ "loading a file from your computer, or searching Gutenberg.org to query books to load.</html>");
		
		GridLayout gridLayout = new GridLayout(5,1,10,10);
		
		menuPanel.setLayout(gridLayout);
		menuPanel.add(menuOverview);
	
		menuPanel.add(stringInput);
		menuPanel.add(fileInput);
		menuPanel.add(databaseInput);
		
		
	}
	
	private void setupStringCard() {
		
		BoxLayout boxLayout = new BoxLayout(stringPanel, BoxLayout.Y_AXIS);
		
		JLabel overView = new JLabel("<html>Type your words in this box to have them counted. The ouput is displayed in the box bellow.</html>");		
		JScrollPane scrollOut;
		JScrollPane scrollIn;
		
		JButton submit = new JButton("Submit");
		JButton clear = new JButton("Clear");
		JButton backButton = new JButton("Main Menu");	
		
		
		
		stringPanel.setLayout(boxLayout);
		
		submit.addActionListener(controller);
		clear.addActionListener(controller);
		backButton.addActionListener(controller);
		stringGraph.addActionListener(controller);
		stringInput.addActionListener(controller);
		
		input.setEditable(true);
		input.setLineWrap(true);
		
		stringGraph.setEnabled(false);
		
		output.setModel(dtm);
		
		scrollOut = new JScrollPane(output);
		scrollIn = new JScrollPane(input);
		
		
		
		stringPanel.add(overView);
		stringPanel.add(scrollIn);
		stringPanel.add(submit);
		stringPanel.add(scrollOut);
		stringPanel.add(stringGraph);
		stringPanel.add(clear);
		stringPanel.add(backButton);
		

	
		
		
	}
	
	private void setupFileCard() {
		
		BoxLayout boxLayout = new BoxLayout(filePanel, BoxLayout.Y_AXIS);
		
		filePanel.setLayout(boxLayout);
		
		
		JLabel fileOutline = new JLabel("<html>Load in a file to be able to have it read by this program.</html>");
		JScrollPane scrollOutput;
	
		
		JButton menuButton = new JButton("Main Menu");	
		JButton findFile = new JButton("Find File");
		JButton clearFileDB = new JButton("Clear File");
		
		
		menuButton.addActionListener(controller);
		fileInput.addActionListener(controller);
		findFile.addActionListener(controller);
		clearFileDB.addActionListener(controller);
		fileGraph.addActionListener(controller);
		
		//TODO figure out why this isn't working
		fileGraph.setEnabled(false);

		
		fileTableOutput.setModel(file_dtm);
		
		scrollOutput = new JScrollPane(fileTableOutput);

		
		
		
		filePanel.add(fileOutline);
		filePanel.add(scrollOutput);
		filePanel.add(findFile);
		filePanel.add(fileGraph);
		filePanel.add(clearFileDB);
		filePanel.add(menuButton);
		
		
	}
	
	private void setupDatabaseCard() {
		
		
		//TODO: This is what I am currently developing. 
	
//		
//		JLabel overView = new JLabel("<html>Type your words in this box to have them counted. The ouput is displayed in the box bellow.</html>");		
//		JScrollPane scrollOut;
//		JScrollPane scrollIn;
//		
//		JButton submit = new JButton("Submit");
//		JButton clear = new JButton("Clear");
//		JButton backButton = new JButton("Main Menu");	
//		
//		
//		databasePanel.setLayout(boxLayoutDB);
//		
//		submit.addActionListener(controller);
//		clear.addActionListener(controller);
//		backButton.addActionListener(controller);
//		
//		input.setEditable(true);
//		input.setLineWrap(true);
//		
//		output.setModel(dtm);
//		
//		scrollOut = new JScrollPane(output);
//		scrollIn = new JScrollPane(input);
//		
//		databasePanel.add(overView);
//		databasePanel.add(scrollIn);
//		databasePanel.add(submit);
//		databasePanel.add(scrollOut);
//		databasePanel.add(clear);
//		databasePanel.add(backButton);
		
		databaseInput.addActionListener(controller);
		
		
	}
	
	public static void main (String[] args){
		WordCounterView x = new WordCounterView();
	}
}
