/* @author: Lynnette Ng 
 * 
 * This is just a class for me to test the adding and splitting of string 
 */

package com.taskpad.tests;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.taskpad.input.Add;

public class TestAddInput {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args){
		Map<String, String> inputParams = new HashMap<String, String>();

		String input = sc.nextLine();
		System.out.println("Input string: " + input);
		
		input = removeFirstWord(input);
		
		Add add = new Add(input);
		inputParams = add.run();
		System.out.println("Size of params: " + inputParams.size());
		
	}
	
	
	private static String removeFirstWord(String input) {
		return input.replace(getFirstWord(input), "").trim();
	}
	
	private static String getFirstWord(String input) {
		String word = input.trim().split("\\s+")[0];
		return word;
	}
}