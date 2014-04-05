package com.taskpad.ui;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class OutputTableFrame extends GuiFrame{
	/**
	 * generated
	 */
	private static final long serialVersionUID = -325278351936827523L;
	
	private JTable _table;
	private GuiTableModel _taskpadTableModel;
	
	protected OutputTableFrame(){
		super();
		intializeOutputTableFrame();
	}

	private void intializeOutputTableFrame() {
		_taskpadTableModel = new GuiTableModel();
		_table = new JTable(_taskpadTableModel);
		JScrollPane scrollPane = new JScrollPane(_table);	
		
		_table.setFillsViewportHeight(true);
		add(scrollPane);
		
		setVisible(false);
		
	}
	
	protected void update(Object[][] objects){
		_taskpadTableModel.refresh(objects);
		_table.setModel(_taskpadTableModel);
	}
	
}
