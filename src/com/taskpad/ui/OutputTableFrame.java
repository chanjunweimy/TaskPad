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

public class OutputTableFrame extends GuiFrame {

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
	private final Font FONT_HEADER_DEFAULT = new Font(FONT_HEADER_TYPE,
			FONT_HEADER_STYLE, FONT_HEADER_SIZE);
	private static final Color COLOR_TABLE_BACKGROUND = new Color(240,248,255);
	
	private ComponentMover _moveOutputBox = new ComponentMover(this);

	
	protected OutputTableFrame() {
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
		
		_table.setBackground(COLOR_TABLE_BACKGROUND);

		setUpColumnWidth();
		
		
		//to make it movable
		_moveOutputBox.registerComponent(_table);
		
		_isHiding = true;
	}

	/**
	 * 
	 */
	private void customizeHeaderStyle() {
		_table.getTableHeader().setFont(FONT_HEADER_DEFAULT);
		_table.getTableHeader().setBackground(Color.black);
		_table.getTableHeader().setForeground(OutputTableFrame.COLOR_TABLE_BACKGROUND);
		
		//disable the reordering option
		_table.getTableHeader().setReorderingAllowed(false);
	}

	protected void setUpFrame() {
		setSize(TABLEFRAME_WIDTH, TABLEFRAME_HEIGHT);
		setLocation((int) (COMPUTER_WIDTH / 2 - TABLEFRAME_WIDTH / 2),
				(int) (COMPUTER_HEIGHT / 2 - TABLEFRAME_HEIGHT / 2 - InputFrame
						.getInitialHeight() / 2));
	}

	protected void refresh(Object[][] objects) {
		reset();
		// _taskpadTableModel.refresh(objects);

		/*
		 * DEBUG for (int i = 0; i < objects.length; i++){ for (int j = 0; j <
		 * objects[i].length; j++){ System.err.println(objects[i][j]); } }
		 */

		for (int i = 0; i < objects.length; i++) {
			_taskpadTableModel.addRow(objects[i]);
		}

		setUpColumnWidth();
		_table.repaint();
	}

	private void setUpColumnWidth() {
		TableColumn column = null;
		int colNo = _table.getColumnCount();
		
		//_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		for (int i = 0; i < colNo; i++) {
			column = _table.getColumnModel().getColumn(i);
			int preferredWidthID = 25;
			int noOfColLeft = 2;
			boolean isDesc = i == 1;
			boolean isID = i == 0;
			if (isID) {
				column.setCellRenderer(new GuiCellRenderer());
				column.setPreferredWidth(preferredWidthID);
			} else if (isDesc) {
				column.setCellRenderer(new GuiCellRenderer());
				column.setPreferredWidth((getWidth() - preferredWidthID -
						noOfColLeft * (getWidth() - preferredWidthID) / (colNo - noOfColLeft)));
			} else {
				column.setCellRenderer(new GuiCellRenderer());
				column.setPreferredWidth((getWidth() - preferredWidthID) / (colNo - noOfColLeft));
			}
		}
	}

	private void reset() {
		_taskpadTableModel.setRowCount(0);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		super.nativeKeyPressed(arg0);

		boolean isCtrlW = arg0.getKeyCode() == NativeKeyEvent.VK_W
				&& NativeInputEvent.getModifiersText(arg0.getModifiers())
						.equals("Ctrl");
		boolean isCtrlS = arg0.getKeyCode() == NativeKeyEvent.VK_S
				&& NativeInputEvent.getModifiersText(arg0.getModifiers())
						.equals("Ctrl");

		if (isCtrlW) {
			scrollUp();

		} else if (isCtrlS) {
			scrollDown();
		}
	}

	private void scrollDown() {
		Runnable downScroller = new BarScroller(false,
				_scrollBox.getVerticalScrollBar());
		SwingUtilities.invokeLater(downScroller);
	}

	private void scrollUp() {
		Runnable upScroller = new BarScroller(true,
				_scrollBox.getVerticalScrollBar());
		SwingUtilities.invokeLater(upScroller);
	}
	
	@Override
	protected void endProgram() {
		super.endProgram();
		
		//clear every listener before closing
		_moveOutputBox.deregisterComponent(_table);
	}

	// /*
	public static void main(String[] args) {
		OutputTableFrame anyTable = new OutputTableFrame();
		anyTable.showWindow(true);
	}
	// */
}
