//@author A0112084U

package com.taskpad.ui;

import javax.swing.table.DefaultTableModel;

public class GuiTableModel extends DefaultTableModel {

	/**
	 * generated
	 */
	private static final long serialVersionUID = 7747422784248466091L;

	private static final String[] _columnNames = { "ID", "Description",
			"Deadline", "Start", "End", "Info", "Status" };

	/*private static Object[][] _data = {
		{"1", "Hi Lynnette!", "06/04/2014", "01:00 05/04/2014", "12:00 05/04/2014", "JUST FOR FUN", "DONE"}
	};*/
	private static Object[][] _data = {};
	
	protected GuiTableModel(){
		super(_data, _columnNames);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// all cells false
		return false;
	}
	
	/*
	public void refresh(Object[][] objects){
	    //make the changes to the table, then call fireTableChanged
		_data = objects;
	    fireTableChanged(null);
	}
	*/

}
