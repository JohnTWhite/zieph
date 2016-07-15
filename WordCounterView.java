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
	
	JScrollPane scrollOut;
	JScrollPane scrollIn;
	
	JButton submit = new JButton("Submit");
	JButton clear = new JButton("Clear");
	
	
	public WordCounterView(){
		
		super("Word Counter");
		setSize(550,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout layout = new GridLayout(5,1,10,10);
		
		submit.addActionListener(controller);
		clear.addActionListener(controller);
		
		output.setEditable(false);
		output.setLineWrap(true);
		
		scrollOut = new JScrollPane(output);
		scrollIn = new JScrollPane(input);
		
		input.setEditable(true);
		input.setLineWrap(true);
		
		panel.setLayout(layout);
	
		panel.add(overView);
		panel.add(scrollIn);
		panel.add(submit);
		panel.add(scrollOut);
		panel.add(clear);
	
		add(panel);
		setVisible(true);
	
		
	}
	
	public static void main (String[] args){
		WordCounterView x = new WordCounterView();
	}
}
