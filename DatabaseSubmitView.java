package WordCount;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;





public class DatabaseSubmitView  extends JFrame{

	//Our swing objects to store our info that will submit to our DB.
	JTextField title = new JTextField("");
	JTextField authorFirst = new JTextField("");
	JTextField authorLast = new JTextField("");
	JLabel titleBlurb = new JLabel("Type in title");
	JLabel authorBlurb = new JLabel("Type in authors First then Last name");
	JLabel genereBlurb = new JLabel("Make sure to Select a catagory as well");
	String[] fiction = {" ","fiction", "non-fiction"};
	JComboBox fictious = new JComboBox(fiction);
	String[] genere = {" ","comedy","drama","action","historical","romance","biography","STEM topic"};
	JComboBox generes = new JComboBox(genere);
	
	JButton submit = new JButton("Submit Information");
	JButton clear = new JButton("Clear Information");
	
	public DatabaseSubmitView(WordCounterModel WCM){
		
		super("Submit to our Database.");
		
		DatabaseController DC = new DatabaseController(WCM, this);
		
		JPanel panel = new JPanel();
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		/*
		 * 
		 * Add document listeners to disable submit until
		 * all fields are complete. Call in our class 
		 * within a class for our document listener, 
		 * and our size listener. 
		 *  
		 */
		
		DatabaseController.listenIfCompleted listen = DC.new listenIfCompleted();
		title.getDocument().addDocumentListener(listen);
		authorFirst.getDocument().addDocumentListener(listen);
		authorLast.getDocument().addDocumentListener(listen);
		DatabaseController.comboListen comboboxListener = DC.new comboListen();
		fictious.addItemListener(comboboxListener);
		generes.addItemListener(comboboxListener);
		
		//Sets size limit's on user inputs.
		
		DatabaseController.SizeFilter sizeTitle = DC.new SizeFilter(50);
		((AbstractDocument)title.getDocument()).setDocumentFilter(sizeTitle);
		DatabaseController.SizeFilter sizeName = DC.new SizeFilter(20);
		((AbstractDocument)authorFirst.getDocument()).setDocumentFilter(sizeName);
		((AbstractDocument)authorLast.getDocument()).setDocumentFilter(sizeName);
		
		
		submit.setEnabled(false);
		submit.addActionListener(DC);

		
		clear.addActionListener(DC);
		
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		panel.add(titleBlurb);
		panel.add(title);
		panel.add(authorBlurb);
		panel.add(authorFirst);
		panel.add(authorLast);
		panel.add(genereBlurb);
		panel.add(fictious);
		panel.add(generes);
		panel.add(submit);
		panel.add(clear);
		
		add(panel);
		setVisible(true);
	}
	
	

}