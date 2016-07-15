package WordCount;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class WordCounterView extends JFrame {

	WordCounterController controller = new WordCounterController(this);
	
	JPanel panel = new JPanel();
	JLabel overView = new JLabel("Type your words to have them counted");
	JTextArea input = new JTextArea();
	JTextArea output = new JTextArea();
//	JScrollBar scroll = new JScrollBar(JScrollBar.VERTICAL);
	
	JButton submit = new JButton("Submit");
	
	
	public WordCounterView(){
		
		super("Word Counter");
		setSize(550,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout layout = new GridLayout(5,1,10,10);
		
		submit.addActionListener(controller);
		
//		BoundedRangeModel scrollView = output.getVerticalVisibility();
		
		output.setEditable(false);
		output.setLineWrap(true);
		
		input.setEditable(true);
		input.setLineWrap(true);
		
		
		
		panel.setLayout(layout);
	
		
		panel.add(overView);
		panel.add(input);
		panel.add(submit);
		panel.add(output);
	
		add(panel);
		setVisible(true);
	
		
	}
	
	public static void main (String[] args){
		WordCounterView x = new WordCounterView();
	}
}
