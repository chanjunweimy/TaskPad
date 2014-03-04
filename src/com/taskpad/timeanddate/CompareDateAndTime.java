package com.taskpad.timeanddate;

import java.text.SimpleDateFormat;
import java.util.Comparator;

import com.taskpad.execute.Task;

//sorting comparator created for executor
public class CompareDateAndTime {
	public static final Comparator<Task> ACCENDING_ORDER = 
			new Comparator<Task>() {
		public int compare(Task e1, Task e2) {
			SimpleDateFormat dateConverter = new SimpleDateFormat();
			return 0;
		}
	};
}
