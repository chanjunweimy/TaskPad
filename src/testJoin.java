import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class testJoin{
	public static void main(String[] args){
		new inputFrame();
		new outputFrame();
	}
}

class inputFrame extends JFrame
{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;  
	protected static JTextField input = new JTextField(15);
	final double COMPUTER_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final double COMPUTER_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	final static int INPUTFRAME_WIDTH = 500;
	final static int INPUTFRAME_HEIGHT = 30;
	
	public inputFrame(){
		initialInputFrame();
	}

	private void initialInputFrame() {
		TextFieldListener tfListener = new TextFieldListener();
		input.addActionListener(tfListener);
		
		input.setBackground(new Color(219, 219, 219));//grey color
		
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setSize(INPUTFRAME_WIDTH,INPUTFRAME_HEIGHT);
		
		int leftShift = INPUTFRAME_WIDTH - outputFrame.OUTPUTFRAME_WIDTH;
		leftShift /= 2;
		
		setLocation((int)(COMPUTER_WIDTH/2 - leftShift),
					(int)(COMPUTER_HEIGHT/2));
		
		this.getContentPane().add(input);
		
		setVisible(true);
		input.requestFocus();        // start with focus on this field
	}

}

class outputFrame extends JFrame{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	protected static JTextArea output = new JTextArea(5, 15);
	final double COMPUTER_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final double COMPUTER_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	final static int OUTPUTFRAME_WIDTH = 300;
	final static int OUTPUTFRAME_HEIGHT = 200;
	
	public outputFrame(){
		initialOutputFrame();
	}

	private void initialOutputFrame() {
		// Don't let the user change the output.
		output.setEditable(false);
		output.setBackground(new Color(242, 242, 242));
		output.setBorder(BorderFactory.createLineBorder(new Color(112, 48, 160)));
		
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setSize(OUTPUTFRAME_WIDTH,OUTPUTFRAME_HEIGHT);
		setLocation((int)(COMPUTER_WIDTH/2),
					(int)(COMPUTER_HEIGHT/2 - OUTPUTFRAME_HEIGHT));
		
		this.getContentPane().add(output);
		
		setVisible(true);
	}

}

class TextFieldListener implements ActionListener
{  
	public void actionPerformed(ActionEvent evt)
	{  
		String inputString = inputFrame.input.getText();
		outputFrame.output.append(inputString + "\n");
		inputFrame.input.setText("");
}
}