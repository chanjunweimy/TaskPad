package com.taskpad.ui;

import javax.swing.table.DefaultTableModel;

public class GuiTableModel extends DefaultTableModel {

	/**
	 * generated
	 */
	private static final long serialVersionUID = 7747422784248466091L;

	private final String[] _columnNames = { "Task ID", "Description",
			"Deadline", "Start", "End", "Details", "Status" };

	private Object[][] _data = {

	};

	@Override
	public int getColumnCount() {
		return _columnNames.length;
	}

	@Override
	public int getRowCount() {
		return _data.length;
	}

	@Override
	public String getColumnName(int col) {
		return _columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		return _data[row][col];
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// all cells false
		return false;
	}
	
	public void refresh(Object[][] objects){
	    //make the changes to the table, then call fireTableChanged
		_data = objects;
	    fireTableChanged(null);
	}

}
