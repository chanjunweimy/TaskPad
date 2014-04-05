package com.taskpad.ui;

//@author A0112084U

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class OutputTableFrame extends GuiFrame{
	/**
	 * generated
	 */
	private static final long serialVersionUID = -325278351936827523L;
	
	private JTable _table;
	private GuiTableModel _taskpadTableModel;
	
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
		JScrollPane scrollPane = new JScrollPane(_table);	
		
		_table.setFillsViewportHeight(true);
		add(scrollPane);	
		
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
		_taskpadTableModel.refresh(objects);
		_table.setModel(_taskpadTableModel);
		setUpColumnWidth();
	}
	
	private void setUpColumnWidth() {
		TableColumn column = null;
		int colNo = _table.getColumnCount();
		for (int i = 0; i < colNo; i++) {
		    column = _table.getColumnModel().getColumn(i);
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

	///*
	public static void main (String[] args){
		OutputTableFrame anyTable = new OutputTableFrame();
		anyTable.showWindow(true);
	}
	//*/
}
