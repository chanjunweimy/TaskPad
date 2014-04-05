package com.taskpad.storage;

//@author A0105788U

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DataManager {

	public DataManager(){
		
	}
	
	public static int retrieveNumberOfTasks(){
		int numberOfTasks = retrieve(DataFileStack.FILE).size();
		return numberOfTasks;
	}
	
	public static void initializeXml(){
		// do nothing
		/*
		LinkedList<Task> tasks = new LinkedList<Task>();
		DataManager.storeBack(tasks, DataFile.FILE);
		DataManager.storeBack(tasks, DataFile.FILE_PREV);
		*/
	}
		
	public static TaskList retrieve(String file) {
		TaskList listOfTasks = new TaskList();
		
		try {
			File fXmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("Task");
			
			// System.out.println("nList.length = " + nList.getLength());
			for (int i = 0; i < nList.getLength(); i++) {
				Node taskNode = nList.item(i);
		 
				if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
					Element task = (Element) taskNode;
					
					String description;
					String deadline;
					String startDate;
					String startTime;
					String endDate;
					String endTime;
					String venue;
					String details;
					int done;
					
					if (task.getElementsByTagName("description").item(0) != null) {
						description = task.getElementsByTagName("description").item(0).getTextContent();
					} else {
						return listOfTasks; // description cannot be null
					}
					
					if(task.getElementsByTagName("deadline").item(0) != null) {
						deadline = task.getElementsByTagName("deadline").item(0).getTextContent();
					} else {
						deadline = null;
					}
					
					if (task.getElementsByTagName("start_date").item(0) != null) {
						startDate = task.getElementsByTagName("start_date").item(0).getTextContent();
					} else {
						startDate = null;
					}
					
					if (task.getElementsByTagName("start_time").item(0) != null) {
						startTime = task.getElementsByTagName("start_time").item(0).getTextContent();
					} else {
						startTime = null;
					}
					
					if (task.getElementsByTagName("end_date").item(0) != null) {
						endDate = task.getElementsByTagName("end_date").item(0).getTextContent();
					} else {
						endDate = null;
					}
					
					if (task.getElementsByTagName("end_time").item(0) != null) {
						endTime = task.getElementsByTagName("end_time").item(0).getTextContent();
					} else {
						endTime = null;
					}
					
					if (task.getElementsByTagName("venue").item(0) != null) {
						venue = task.getElementsByTagName("venue").item(0).getTextContent();
					} else {
						venue = null;
					}
					
					if(task.getElementsByTagName("details").item(0) != null) {
						details = task.getElementsByTagName("details").item(0).getTextContent();
					} else {
						details = null;
					}
					
					assert(task.getElementsByTagName("done") != null);
					done = Integer.parseInt(task.getElementsByTagName("done").item(0).getTextContent());
					
					listOfTasks.add(new Task(description, deadline, startDate,
							startTime, endDate, endTime, venue, details, done));
				}
			}
			
			return listOfTasks;
	    } catch (FileNotFoundException e) {
			TaskList tasks = new TaskList();
			DataManager.storeBack(tasks, file);
			return tasks;
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listOfTasks;
		
	}
	
	public static void storeBack(TaskList taskList, String file) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("TaskPad");
			doc.appendChild(rootElement);
	 
			for(int i = 0; i < taskList.size(); i++) {
				Task taskInList = taskList.get(i);
				int id = i + 1;
				Element task = doc.createElement("Task");
				rootElement.appendChild(task);
				task.setAttribute("id", Integer.toString(id));
				
				if(taskInList.getDescription() != null) {
					Element description = doc.createElement("description");
					description.appendChild(doc.createTextNode(taskInList.getDescription()));
					task.appendChild(description);
				}
				
				if(taskInList.getDeadline() != null) {
					Element deadlineDay = doc.createElement("deadline");
					deadlineDay.appendChild(doc.createTextNode(taskInList.getDeadline()));
					task.appendChild(deadlineDay);
				}
				
				if (taskInList.getStartDate() != null) {
					Element startDate = doc.createElement("start_date");
					startDate.appendChild(doc.createTextNode(taskInList.getStartDate()));
					task.appendChild(startDate);
				}
				
				if (taskInList.getStartTime() != null) {
					Element startTime = doc.createElement("start_time");
					startTime.appendChild(doc.createTextNode(taskInList.getStartTime()));
					task.appendChild(startTime);
				}
				
				if (taskInList.getEndDate() != null) {
					Element endDate = doc.createElement("end_date");
					endDate.appendChild(doc.createTextNode(taskInList.getEndDate()));
					task.appendChild(endDate);
				}
				
				if (taskInList.getEndTime() != null) {
					Element endTime = doc.createElement("end_time");
					endTime.appendChild(doc.createTextNode(taskInList.getEndTime()));
					task.appendChild(endTime);
				}
				
				if (taskInList.getDetails() != null) {
					Element details = doc.createElement("details");
					details.appendChild(doc.createTextNode(taskInList.getDetails()));
					task.appendChild(details);
				}
				
				Element done = doc.createElement("done");
				done.appendChild(doc.createTextNode(Integer.toString(taskInList.getDone())));
				task.appendChild(done);
				
			}
				
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(file));
			
			transformer.transform(source, result);
	 
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
		  	tfe.printStackTrace();
		}
	
	}
	
}
