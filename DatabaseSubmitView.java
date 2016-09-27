package WordCount;

import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionListener;




public class DatabaseSubmitView  extends JFrame{


	JTextField title = new JTextField("title");
	JTextField authorFirst = new JTextField("author's first name");
	JTextField authorLast = new JTextField("author's last name");
	String[] fiction = {"pick a type","fiction", "non-fiction"};
	JComboBox fictious = new JComboBox(fiction);
	String[] genere = {"pick a genere","comedy","drama","historical","romance","science/math"};
	JComboBox generes = new JComboBox(genere);
	
	JButton submit = new JButton("Submit Information");
	JButton clear = new JButton("Clear Information");
	
	public DatabaseSubmitView(WordCounterModel WCM){
		
		super("Submit to our Database.");
		
		DatabaseController DC = new DatabaseController(WCM);
		
		JPanel panel = new JPanel();
		setSize(500, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Sets size limit's on user inputs.
		((AbstractDocument)title.getDocument()).setDocumentFilter(new SizeFilter(50));
		((AbstractDocument)authorFirst.getDocument()).setDocumentFilter(new SizeFilter(20));
		((AbstractDocument)authorLast.getDocument()).setDocumentFilter(new SizeFilter(20));
		
		
		//TODO: if there are problems check this out.
		submit.addActionListener(DC);
		
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		panel.add(title);
		panel.add(authorFirst);
		panel.add(authorLast);
		panel.add(fictious);
		panel.add(generes);
		panel.add(submit);
		panel.add(clear);
		
		add(panel);
		setVisible(true);
	}
	
	

public class SizeFilter extends DocumentFilter {

    private int maxCharacters;

    public SizeFilter(int maxChars) {
        maxCharacters = maxChars;
    }

    public void insertString(FilterBypass fb, int offs, String str, javax.swing.text.AttributeSet a)
                    throws BadLocationException {

        if ((fb.getDocument().getLength() + str.length()) <= maxCharacters) {
            super.insertString(fb, offs, str, a);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    public void replace(FilterBypass fb, int offs, int length, String str, javax.swing.text.AttributeSet a)
                    throws BadLocationException {

        if ((fb.getDocument().getLength() + str.length()
                        - length) <= maxCharacters) {
            super.replace(fb, offs, length, str, a);
        } 
    }
}

}