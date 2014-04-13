//@author A0105788U

package com.taskpad.storage;

import java.util.LinkedList;

/**
 * TaskList
 * 
 * This is the TaskList class to maintain a list of tasks at runtime.
 * 
 */
public class TaskList {
	private LinkedList<Task> list;
	
	public TaskList() {
		list = new LinkedList<Task>();
	}
	
	public LinkedList<Task> getList() {
		return list;
	}
	
	public void add(Task task) {
		list.add(task);
	}
	
	public Task get(int index) {
		return list.get(index);
	}
	
	public int size() {
		return list.size();
	}
	
	public void remove(int index) {
		list.remove(index);
	}
}
