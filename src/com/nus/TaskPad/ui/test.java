package com.nus.TaskPad.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

public class test {

        public static void main(String[] args) {
                new test();
        }

        public test() {
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
                                frame.add(new TestPane());
                                frame.pack();
                                frame.setLocation((int)(COMPUTER_WIDTH/2.5),(int)(COMPUTER_HEIGHT/10.0));
                                //frame.setLocationRelativeTo(null);//make centre
                                frame.setSize(300,250);
                                frame.setVisible(true);
                        }
                });
        }

        public class TestPane extends JPanel {

                /**
                 * 
                 */
                private static final long serialVersionUID = 1L;
                private JList<CharSequence> list;
                private DefaultListModel<CharSequence> model;

                public TestPane() {
                        setLayout(new BorderLayout());
                        setBorder(new LineBorder(Color.BLACK));
                        model = new DefaultListModel<CharSequence>();
                        list = new JList<CharSequence>(model);
                        //list.setBounds(-150, -1000, 220, 150);
                        list.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                                        Integer.MAX_VALUE));
                        doAction();

                        add(list);
                }

                public void doAction() {

                        String s = "random";

                        if (!model.isEmpty()) {
                                model.clear();
                        }

                        model.addElement(s);
                }
        }
}
