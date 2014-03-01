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
		int numberOfTasks = retrieve(file).size();	//Please call your function, thanks! Needed for input manager
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
			
			NodeList nList = doc.getElementsByTagName("task");
			
			for (int i = 0; i < nList.getLength(); i++) {
				Node taskNode = nList.item(i);
		 
				if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
					Element task = (Element) taskNode;
		 
					String description = task.getElementsByTagName("description").item(0).getTextContent();
					String deadline = task.getElementsByTagName("deadline").item(0).getTextContent();
					String startTime = task.getElementsByTagName("start_time").item(0).getTextContent();
					String endTime = task.getElementsByTagName("end_time").item(0).getTextContent();
					String details = task.getElementsByTagName("details").item(0).getTextContent();
					int done = Integer.parseInt(task.getElementsByTagName("done").item(0).getTextContent());
					
					listOfTasks.add(new Task(description, deadline, startTime, endTime, details, done));
				}
			}
			
			return listOfTasks;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		
		return null;
	}
	
	public void storeBack(LinkedList<Task> taskList, String file) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("TaskPad");
			doc.appendChild(rootElement);
	 
			for(Task taskInList: taskList) {
				Element task = doc.createElement("Task");
				rootElement.appendChild(task);
				// task.setAttribute("id", "1");
				
				Element description = doc.createElement("description");
				description.appendChild(doc.createTextNode(taskInList.getDescription()));
				task.appendChild(description);
				
				Element deadline = doc.createElement("deadline");
				deadline.appendChild(doc.createTextNode(taskInList.getDeadline()));
				task.appendChild(deadline);
				
				Element startTime = doc.createElement("start_time");
				startTime.appendChild(doc.createTextNode(taskInList.getStartTime()));
				task.appendChild(startTime);
				
				Element endTime = doc.createElement("end_time");
				endTime.appendChild(doc.createTextNode(taskInList.getEndTime()));
				task.appendChild(endTime);
				
				Element details = doc.createElement("details");
				details.appendChild(doc.createTextNode(taskInList.getDetails()));
				task.appendChild(details);
				
				Element done = doc.createElement("done");
				done.appendChild(doc.createTextNode(taskInList.getDone()));
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
