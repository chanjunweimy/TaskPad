//@author A0112084U

package com.taskpad.ui;

import java.awt.Color;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import com.taskpad.alarm.AlarmManager;
import com.taskpad.input.InputManager;

public class GuiManager {
	private final static Logger LOGGER = Logger.getLogger("TaskPad");

	private static final String NEWLINE = "\n\n";
	private static final String MESSAGE_START_REMINDER = "Today's Tasks ";
	private static InputFrame _inputFrame;
	private static OutputFrame _outputFrame;
	private static OutputTableFrame _tableFrame;
	private static boolean _isDebug = false;
	private static boolean _isTableCalled = false;

	// not designed to be instantiated
	private GuiManager() {
	}

	// invoke all frames to a thread
	public static void initialGuiManager() {
		initializeTableFrame();
		initializeFlexiFontOutputFrame();
		initializeInputFrame();
	}

	/**
	 * 
	 */
	private static void initializeInputFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_inputFrame = new InputFrame();
			}

		});
	}

	/**
	 * 
	 */
	private static void initializeTableFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_tableFrame = new OutputTableFrame();
			}

		});
	}

	/**
	 * 
	 */
	private static void initializeFlexiFontOutputFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_outputFrame = new FlexiFontOutputFrame();
			}

		});
	}

	/*
	 * deprecated public static void initialGuiManager(InputFrame inputFrame,
	 * OutputFrame outputFrame) { setInputFrame(inputFrame);
	 * setOutputFrame(outputFrame); }
	 */

	public static void callTable(final Object[][] data) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swapFrame(_outputFrame, _tableFrame);
				_tableFrame.refresh(data);
				_inputFrame.requestFocusOnInputBox();
			}

		});
	}

	/**
	 * @param data
	 */
	private static void swapFrame(final GuiFrame firstFrame,
			final GuiFrame secondFrame) {

		boolean isShown = firstFrame.isVisible() == true;
		boolean isHided = firstFrame.isVisible() == false;

		LOGGER.info("isShown is : " + isShown);
		LOGGER.info("isHided is : " + isHided);

		if (isHided) {
			LOGGER.info("IS NOT SWAPPING!!! ");
			
			firstFrame.hideWindow();
			secondFrame.showUp(secondFrame);
			
			return;
		} else if (isShown) {
			LOGGER.info("IS SWAPPING!!! ");

			firstFrame.hideWindow();
			secondFrame.showUp(firstFrame);
			_isTableCalled = !_isTableCalled;

			LOGGER.info("HAS SWAPPED ");
		}
	}

	public static void showWindow(final boolean isVisible) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_inputFrame.showWindow(isVisible);
				swapFrame(_tableFrame, _outputFrame);
				_inputFrame.requestFocusOnInputBox();
			}

		});

	}

	public static void callExit() {
		closeAllWindows();

	}

	private static void closeAllWindows() {

		_inputFrame.close();
		_outputFrame.close();
		_tableFrame.close();

	}

	public static void callOutput(final String out) {
		callOutputNoLine(out + NEWLINE);

	}

	public static void callOutputNoLine(final String out) {
		if (!_isDebug) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					swapFrame(_tableFrame, _outputFrame);
					_outputFrame.addLine(out);

					_inputFrame.requestFocusOnInputBox();
				}

			});

		} else {
			System.out.println(out);
		}

	}

	/**
	 * @deprecated
	 * @param out
	 */
	protected static void callInputBox(String out) {
		_inputFrame.setLine(out);
	}

	public static void showSelfDefinedMessage(String out, Color c,
			boolean isBold) {
		showSelfDefinedMessageNoNewline(out + NEWLINE, c, isBold);

	}

	public static void showSelfDefinedMessageNoNewline(final String out,
			final Color c, final boolean isBold) {
		if (!_isDebug) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					swapFrame(_tableFrame, _outputFrame);
					_outputFrame.addSelfDefinedLine(out, c, isBold);

					_inputFrame.requestFocusOnInputBox();
				}

			});

		} else {
			System.out.println(out);
		}

	}

	public static void startRemindingUser() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swapFrame(_tableFrame, _outputFrame);
				remindUser(MESSAGE_START_REMINDER);
			}

		});

	}

	private static void remindUser(final String out) {
		// swapFrame(_tableFrame, _outputFrame);
		_outputFrame.addReminder(out + NEWLINE);
	}

	protected static void passInput(final String in) {
		InputManager.receiveFromGui(in);
	}

	protected static void turnOffAlarm() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AlarmManager.turnOffAlarm();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	protected static void cancelAlarms() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AlarmManager.cancelAlarms();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	protected static OutputFrame getOutputFrame() {
		return _outputFrame;
	}

	public static void clearOutput() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swapFrame(_tableFrame, _outputFrame);
				_outputFrame.clearOutputBox();
				_inputFrame.requestFocusOnInputBox();
			}

		});
		
	}

	// for debug
	public static boolean getInputFrameVisibility() {
		boolean isNormal = _inputFrame.isVisible();
		return isNormal;
	}

	public static boolean getOutputFrameVisibility() {
		boolean isNormal = _outputFrame.isVisible();
		return isNormal;
	}

	public static boolean getTableVisibility() {
		boolean isNormal = _tableFrame.isVisible();
		return isNormal;
	}

	public static boolean isTableActive() {
		return _isTableCalled;
	}

	public static void setDebug(boolean isDebugFlag) {
		_isDebug = isDebugFlag;
		_isTableCalled = false;
	}

	/*
	 * deprecated private static void setInputFrame(InputFrame _inputFrame) {
	 * GuiManager._inputFrame = _inputFrame; }
	 * 
	 * private static void setOutputFrame(OutputFrame _outputFrame) {
	 * GuiManager._outputFrame = _outputFrame; }
	 */

}
