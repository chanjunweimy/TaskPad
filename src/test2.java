
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;


public class test2 {

	public static void main(String[] args) {
		new test2();
	}

	public test2() {
		final double COMPUTER_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		final double COMPUTER_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
				}

				JFrame frame = new JFrame("Testing");
				frame.setUndecorated(true);//to hide the top level container
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(new InputPanel());
				frame.pack();
				frame.setLocation((int)(COMPUTER_WIDTH/2.5),(int)(COMPUTER_HEIGHT/10.0));
				//frame.setLocationRelativeTo(null);//make centre
				frame.setSize(300,250);
				frame.setVisible(true);
			}
		});
	}
	
	public class InputPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTextField txtbox = new JTextField(20);
		//private JTextArea output = new JTextArea(5, 15);

		public InputPanel() {
			setLayout(new BorderLayout());
			setBorder(new LineBorder(Color.BLACK));
			
			
			TextFieldListener tfListener = new TextFieldListener();
			txtbox.addActionListener(tfListener);
			txtbox.requestFocus();
			txtbox.setLocation(0, 0);
			
			//output.setEditable(false);
			//output.setLocation(0, -1000);
			
			add(txtbox);
			//add(output);
		}
	
	// The listener for the textfield.
		private class TextFieldListener implements ActionListener
		{  public void actionPerformed(ActionEvent evt)
		{  String inputString = txtbox.getText();
		//output.append(inputString + "\n");
		txtbox.setText("");
		}
		}
	}
}