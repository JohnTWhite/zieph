package WordCount;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;


public class WordCounterView extends JFrame {

	
//Bring in our controller. Which holds our action listener events and our functions.
	
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
		//TODO: delete this when done
		JButton deletethisbutton = new JButton("TRIAL");
	

//	This panel is for the string input UI elements. 
	
	JPanel stringPanel = new JPanel();
	
	// TODO: See if these can go into the cards
	
		JTextArea input = new JTextArea(20,20);
//		JTable output = new JTable(20,20);
		JTable dbOutput = new JTable(20,20);
		JTable fileTableOutput = new JTable(20,20);
		JButton stringGraph = new JButton("Graph String");
		JButton stringDB = new JButton("Commit to DB");
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
		//TODO: delete this when done.
		deletethisbutton.addActionListener(controller);
		menuPanel.add(deletethisbutton);
		
		
		
	}
	
	private void setupStringCard() {
		
		
		JTable output = new JTable(20,20);
		

		
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
		stringDB.addActionListener(controller);
		stringInput.addActionListener(controller);
		
		input.setEditable(true);
		input.setLineWrap(true);
		
		stringGraph.setEnabled(false);
		stringDB.setEnabled(false);
		
		output.setModel(dtm);
		
		scrollOut = new JScrollPane(output);
		scrollIn = new JScrollPane(input);
		
		
		
		stringPanel.add(overView);
		stringPanel.add(scrollIn);
		stringPanel.add(submit);
		stringPanel.add(scrollOut);
		stringPanel.add(stringGraph);
		stringPanel.add(stringDB);
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
//		JButton fileGraph = new JButton("Graph File");
		
		//TODO figure out why this isn't working
		fileGraph.setEnabled(false);
		
		
		menuButton.addActionListener(controller);
		fileInput.addActionListener(controller);
		findFile.addActionListener(controller);
		clearFileDB.addActionListener(controller);
		fileGraph.addActionListener(controller);
		


		
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
		
		DatabaseController DBcontroller = new DatabaseController();
		
		
		BoxLayout boxLayout = new BoxLayout(databasePanel, BoxLayout.Y_AXIS);
		databasePanel.setLayout(boxLayout);
		
	
		
		JLabel dbOverView = new JLabel("<html>You can access our database system here, "
				+ "this will hold any sample sets that have been submited. "
				+ "Sample sets that include your own submitted works! </html>");		
		
		//this combobox will hold our table names on the sql db, or the option for all data.
		
		String[] comboBoxContent = DBcontroller.allTitles();
		
		JComboBox allTitles = new JComboBox(comboBoxContent);
		//this scroll pane will hold the JTable that will hold our data that has been requested 
		//by the user.
		
		JScrollPane dbScrollOutput;
		
		JTable output = new JTable(20,20);


		JButton dbSubmit = new JButton("Submit to database");
		JButton dbClear = new JButton("Clear table");
		JButton backButton = new JButton("Main Menu");		
		

		dbSubmit.addActionListener(controller);
		dbClear.addActionListener(controller);
		backButton.addActionListener(controller);
		
		
		output.setModel(dtm);
		
		dbScrollOutput = new JScrollPane(output);
		
		databasePanel.add(dbOverView);
		databasePanel.add(allTitles);
		databasePanel.add(dbSubmit);
		databasePanel.add(dbScrollOutput);
		databasePanel.add(dbClear);
		databasePanel.add(backButton);
		
		databaseInput.addActionListener(controller);
		
		
	}
	
	public static void main (String[] args){
		WordCounterView x = new WordCounterView();
	}
}
