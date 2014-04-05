package com.taskpad.dateandtime;

/**
 * DayObject: an object to save "holiday" name and date
 */

//@author A0112084U

public class DayObject {
	
	private String _name;
	private int _dateDay;
	private int _dateMonth;
	private int _dateYear;
	
	protected DayObject(String name){
	}

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public int getDateDay() {
		return _dateDay;
	}

	public void setDateDay(int _dateDay) {
		this._dateDay = _dateDay;
	}

	public int getDateMonth() {
		return _dateMonth;
	}

	public void setDateMonth(int _dateMonth) {
		this._dateMonth = _dateMonth;
	}

	public int getDateYear() {
		return _dateYear;
	}

	public void setDateYear(int _dateYear) {
		this._dateYear = _dateYear;
	}
	
}
