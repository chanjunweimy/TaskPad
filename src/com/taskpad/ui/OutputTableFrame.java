//@author A0112084U

package com.taskpad.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;

public class OutputTableFrame extends GuiFrame{
	/**
	 * generated
	 */
	private static final long serialVersionUID = -325278351936827523L;
	
	private JTable _table;
	private GuiTableModel _taskpadTableModel;
	private JScrollPane _scrollBox;
	
	private final static int TABLEFRAME_WIDTH = 480;
	private final static int TABLEFRAME_HEIGHT = 350;
	
	private final String FONT_HEADER_TYPE = "Lucida Console";
	private final int FONT_HEADER_SIZE = 12;
	private final int FONT_HEADER_STYLE = Font.BOLD;
	private final Font FONT_HEADER_DEFAULT = new Font(FONT_HEADER_TYPE, FONT_HEADER_STYLE, FONT_HEADER_SIZE);
	
	protected OutputTableFrame(){
		super();
		intializeOutputTableFrame();
	}
	
	private void intializeOutputTableFrame() {
		setUpFrame();
		setVisible(false);
		
		_taskpadTableModel = new GuiTableModel();
		_table = new JTable(_taskpadTableModel);
		_scrollBox = new JScrollPane(_table);	
		
		_table.setFillsViewportHeight(true);
		add(_scrollBox);	
		
		customizeHeaderStyle();
		
		setUpColumnWidth();
	}

	/**
	 * 
	 */
	private void customizeHeaderStyle() {
		_table.getTableHeader().setFont(FONT_HEADER_DEFAULT);
		_table.getTableHeader().setBackground(Color.black);
		_table.getTableHeader().setForeground(Color.white);
	}
	
	protected void setUpFrame() {
		setSize(TABLEFRAME_WIDTH, TABLEFRAME_HEIGHT);
		setLocation((int)(COMPUTER_WIDTH / 2 - TABLEFRAME_WIDTH / 2),
					(int)(COMPUTER_HEIGHT / 2 - TABLEFRAME_HEIGHT / 2 - InputFrame.getInitialHeight() / 2));
	}
	
	protected void refresh(Object[][] objects){
		reset();
		//_taskpadTableModel.refresh(objects);
		
		/* DEBUG
		for (int i = 0; i < objects.length; i++){
			for (int j = 0; j < objects[i].length; j++){
				System.err.println(objects[i][j]);
			}
		}
		*/
		
		for (int i = 0; i < objects.length; i++){
			_taskpadTableModel.addRow(objects[i]);
		}
		
		setUpColumnWidth();
	    _table.repaint();
	}
	
	private void setUpColumnWidth() {
		TableColumn column = null;
		int colNo = _table.getColumnCount();
		for (int i = 0; i < colNo; i++) {
		    column = _table.getColumnModel().getColumn(i);
		    if(i==0){
		    	column.setPreferredWidth(1);;
		    	column.setCellRenderer(new GuiCellRenderer());
		    } else {
			    int divider = 3;
				boolean isDesc = i == 1;
				if (isDesc) {
			        column.setPreferredWidth(getWidth() / divider); 
			        column.setCellRenderer(new GuiCellRenderer());
			    } else {
			        column.setPreferredWidth(getWidth() / (divider * (colNo - 1)));
			        column.setCellRenderer(new GuiCellRenderer());
			    }
		    }
		}
	}
	
	private void reset() {
		  _taskpadTableModel.setRowCount(0);
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		super.nativeKeyPressed(arg0);
		
		boolean isCtrlW= arg0.getKeyCode() == NativeKeyEvent.VK_W
				&& NativeInputEvent.getModifiersText(arg0.getModifiers()).equals(
	                    "Ctrl");
		boolean isCtrlS = arg0.getKeyCode() == NativeKeyEvent.VK_S				
				&& NativeInputEvent.getModifiersText(arg0.getModifiers()).equals(
	                    "Ctrl");

		
		if (isCtrlW){
			scrollUp();
			
		} else if (isCtrlS){
			scrollDown();
		}
	}

	private void scrollDown() {
		Runnable downScroller = new BarScroller(false, _scrollBox.getVerticalScrollBar());
		SwingUtilities.invokeLater(downScroller);
	}

	private void scrollUp() {
		Runnable upScroller = new BarScroller(true, _scrollBox.getVerticalScrollBar());
		SwingUtilities.invokeLater(upScroller);
	}

	///*
	public static void main (String[] args){
		OutputTableFrame anyTable = new OutputTableFrame();
		anyTable.showWindow(true);
	}
	//*/
}
