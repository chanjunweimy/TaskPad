package com.taskpad.execute;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.taskpad.storage.Task;

public class TaskDeadlineComparator implements Comparator<Task>{
	/**
	 * compare: compare two tasks' Date
	 * 
	 * @param e1
	 *            : task1
	 * @param e2
	 *            : task2
	 * @return int
	 */
	@Override
	public int compare(Task e1, Task e2) {
		SimpleDateFormat dateConverter = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm");
		Date d1, d2;
		try {
			d1 = dateConverter.parse(e1.getDeadline() + e1.getEndTime());
			d2 = dateConverter.parse(e2.getDeadline() + e2.getEndTime());
		} catch (ParseException e) {
			System.err.println(e.getMessage());
			return 0;
		}
		return d1.compareTo(d2);
	}
}
