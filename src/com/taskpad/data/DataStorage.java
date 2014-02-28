package com.taskpad.data;

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
	
	public static void storeBack(LinkedList<Task> taskList) {
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
				Element firstname = doc.createElement("firstname");
				firstname.appendChild(doc.createTextNode("yong"));
				staff.appendChild(firstname);
				
			}
 
		} catch (ParserConfigurationException pce) {
		  	pce.printStackTrace();
		} catch (TransformerException tfe) {
		  	tfe.printStackTrace();
		}

	}
}
