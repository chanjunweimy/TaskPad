package com.taskpad.storage;

//@author A0105788U

import java.util.LinkedList;

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
