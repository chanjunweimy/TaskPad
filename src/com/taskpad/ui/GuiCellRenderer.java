package com.taskpad.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

//@author A0112084U

/**
 * The standard class for rendering (displaying) individual cells in a JTable.
 * This class inherits from JTextArea, a standard component class. However
 * JTextArea is a multi-line area that displays plain text.
 * 
 * This class implements TableCellRenderer , i.e. interface. This interface
 * defines the method required by any object that would like to be a renderer
 * for cells in a JTable.
 * 
 * extracted from:
 * http://manivelcode.blogspot.sg/2008/08/how-to-wrap-text-inside
 * -cells-of-jtable.html
 * 
 * @see JTable
 * @see JTextArea
 */

public class GuiCellRenderer extends JTextArea implements TableCellRenderer {

	/**
	 * generated
	 */
	private static final long serialVersionUID = -1333277163624784049L;
	
	private static final int STAT = 6;
	private static final int INFO = 5;
	private static final int END = 4;
	private static final int START = 3;
	private static final int DEADLINE = 2;
	private static final int DESC = 1;
	private static final int ID = 0;
	
	private static final int FONT_SIZE = 10;
	private static final int FONT_STYLE = Font.BOLD;
	private static final String FONT_TYPE = "Verdana";
	private static final Font FONT_DEFAULT = new Font(FONT_TYPE, FONT_STYLE, GuiCellRenderer.FONT_SIZE);

	private final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	// Column heights are placed in this Map
	private final Map<JTable, Map<Object, Map<Object, Integer>>> tablecellSizes = new HashMap<JTable, Map<Object, Map<Object, Integer>>>();

	/**
	 * Creates a text area renderer.
	 */
	public GuiCellRenderer() {
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	/**
	 * Returns the component used for drawing the cell. This method is used to
	 * configure the renderer appropriately before drawing.
	 * 
	 * @param table
	 *            - JTable object
	 * @param value
	 *            - the value of the cell to be rendered.
	 * @param isSelected
	 *            - isSelected true if the cell is to be rendered with the
	 *            selection highlighted; otherwise false.
	 * @param hasFocus
	 *            - if true, render cell appropriately.
	 * @param row
	 *            - The row index of the cell being drawn.
	 * @param column
	 *            - The column index of the cell being drawn.
	 * @return - Returns the component used for drawing the cell.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// set the Font, Color, etc.
		renderer.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
		// setForeground(renderer.getForeground());
		setBackground(renderer.getBackground());
		setBorder(renderer.getBorder());
		//setFont(renderer.getFont());
		setText(renderer.getText());
		setFont(FONT_DEFAULT);
		
		setFontColor(column);

		TableColumnModel columnModel = table.getColumnModel();

		setSize(columnModel.getColumn(column).getWidth(), 0);

		int heightWanted = (int) getPreferredSize().getHeight();

		addSize(table, row, column, heightWanted);

		heightWanted = findTotalMaximumRowSize(table, row);

		if (heightWanted != table.getRowHeight(row)) {
			table.setRowHeight(row, heightWanted);
		}
		return this;
	}

	/**
	 * @param row
	 * @param column
	 */
	private void setFontColor(int column) {
		switch (column) {
		case ID:
			setForeground(Color.cyan);
			break;

		case DESC:
			setForeground(Color.blue);
			break;

		case DEADLINE:
			setForeground(Color.red);
			break;

		case START:
			setForeground(Color.orange);
			break;

		case END:
			setForeground(Color.pink);
			break;

		case INFO:
			setForeground(Color.gray);
			break;

		case STAT:
			setForeground(Color.magenta);
			break;

		default:
			assert (false);
			break;
		}
	}

	/**
	 * @param table
	 *            - JTable object
	 * @param row
	 *            - The row index of the cell being drawn.
	 * @param column
	 *            - The column index of the cell being drawn.
	 * @param height
	 *            - Row cell height as int value This method will add size to
	 *            cell based on row and column number
	 */
	private void addSize(JTable table, int row, int column, int height) {
		Map<Object, Map<Object, Integer>> rowsMap = tablecellSizes.get(table);

		if (rowsMap == null) {
			tablecellSizes.put(table,
					rowsMap = new HashMap<Object, Map<Object, Integer>>());
		}

		Map<Object, Integer> rowheightsMap = rowsMap.get(row);
		if (rowheightsMap == null) {
			rowsMap.put(row, rowheightsMap = new HashMap<Object, Integer>());
		}

		rowheightsMap.put(column, height);
	}

	/**
	 * Look through all columns and get the renderer. If it is also a
	 * TextAreaRenderer, we look at the maximum height in its hash table for
	 * this row.
	 * 
	 * @param table
	 *            -JTable object
	 * @param row
	 *            - The row index of the cell being drawn.
	 * @return row maximum height as integer value
	 */
	private int findTotalMaximumRowSize(JTable table, int row) {
		int maximum_height = 0;
		Enumeration<TableColumn> columns = table.getColumnModel().getColumns();

		while (columns.hasMoreElements()) {
			TableColumn tc = columns.nextElement();
			TableCellRenderer cellRenderer = tc.getCellRenderer();
			if (cellRenderer instanceof GuiCellRenderer) {
				GuiCellRenderer tar = (GuiCellRenderer) cellRenderer;
				maximum_height = Math.max(maximum_height,
						tar.findMaximumRowSize(table, row));
			}
		}
		return maximum_height;
	}

	/**
	 * This will find the maximum row size
	 * 
	 * @param table
	 *            - JTable object
	 * @param row
	 *            - The row index of the cell being drawn.
	 * @return row maximum height as integer value
	 */
	private int findMaximumRowSize(JTable table, int row) {
		Map<Object, Map<Object, Integer>> rows = tablecellSizes.get(table);
		if (rows == null) {
			return 0;
		}
		Map<Object, Integer> rowheights = rows.get(row);
		if (rowheights == null) {
			return 0;
		}

		int maximum_height = 0;
		for (Map.Entry<Object, Integer> entry : rowheights.entrySet()) {
			int cellHeight = entry.getValue();
			maximum_height = Math.max(maximum_height, cellHeight);
		}
		return maximum_height;
	}
}
