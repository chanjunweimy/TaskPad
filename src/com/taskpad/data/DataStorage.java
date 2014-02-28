package com.taskpad.data;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.taskpad.execute.Task;

public class DataStorage {
	public static LinkedList<Task> retrieve() {
		return null;
	}
	
	public static void storeBack(LinkedList<Task> taskList, String file) {
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
