package com.taskpad.data;

import java.io.File;
import java.util.LinkedList;

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

import com.taskpad.execute.Task;

public class DataManager {

	public DataManager(){
		
	}
	
	public int retrieveNumberOfTasks(String file){
		int numberOfTasks = retrieve(file).size();
		return numberOfTasks;
	}
		
	public static LinkedList<Task> retrieve(String file) {
		LinkedList<Task> listOfTasks = new LinkedList<Task>();
		
		try {
			File fXmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("TaskPad");
			
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
						// description = null;
						return listOfTasks; // description cannot be null
					}
					
					if(task.getElementsByTagName("deadline_day").item(0) != null) {
						deadlineDay = task.getElementsByTagName("deadline_day").item(0).getTextContent();
					} else {
						deadlineDay = null;
					}
					
					if (task.getElementsByTagName("deadline_month").item(0) != null) {
						deadlineMonth = task.getElementsByTagName("deadline_month").item(0).getTextContent();
					} else {
						deadlineMonth = null;
					}
					
					if(task.getElementsByTagName("deadline_year").item(0) != null) {
						deadlineYear = task.getElementsByTagName("deadline_year").item(0).getTextContent();
					} else {
						deadlineYear = null;
					}
					
					if (task.getElementsByTagName("start_time").item(0) != null) {
						startTime = task.getElementsByTagName("start_time").item(0).getTextContent();
					} else {
						startTime = null;
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
					
					done = Integer.parseInt(task.getElementsByTagName("done").item(0).getTextContent());
					
					listOfTasks.add(new Task(description, deadlineDay, deadlineMonth, deadlineYear,
							startTime, endTime, venue, details, done));
				}
			}
			
			return listOfTasks;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		
		return null;
	}
	
	public static void storeBack(LinkedList<Task> taskList, String file) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("TaskPad");
			doc.appendChild(rootElement);
	 
			for(Task taskInList: taskList) {
				Element task = doc.createElement("Task");
				rootElement.appendChild(task);
				// task.setAttribute("id", "1");
				
				if(taskInList.getDescription() != null) {
					Element description = doc.createElement("description");
					description.appendChild(doc.createTextNode(taskInList.getDescription()));
					task.appendChild(description);
				}
				
				if(taskInList.getDeadlineDay() != null) {
					Element deadlineDay = doc.createElement("deadline_day");
					deadlineDay.appendChild(doc.createTextNode(taskInList.getDeadlineDay()));
					task.appendChild(deadlineDay);
				}
				
				if(taskInList.getDeadlineMonth() != null) {
					Element deadlineMonth = doc.createElement("deadline_month");
					deadlineMonth.appendChild(doc.createTextNode(taskInList.getDeadlineMonth()));
					task.appendChild(deadlineMonth);
				}
				
				if (taskInList.getDeadlineYear() != null) {
					Element deadlineYear = doc.createElement("deadline_year");
					deadlineYear.appendChild(doc.createTextNode(taskInList.getDeadlineYear()));
					task.appendChild(deadlineYear);
				}
				
				if (taskInList.getStartTime() != null) {
					Element startTime = doc.createElement("start_time");
					startTime.appendChild(doc.createTextNode(taskInList.getStartTime()));
					task.appendChild(startTime);
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
