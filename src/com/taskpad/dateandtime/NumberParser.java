package com.taskpad.dateandtime;

//@author A0112084U

import java.util.HashMap;
import java.util.Map;

public class NumberParser {

	private Map<String, Integer>  _numberMap = new HashMap<String, Integer>();
	
	private final Integer singleDigitChecker = 10;
	private final String STRING_EMPTY = "";
	private final String[] _tensNames = {
		"zero",
		"ten",
		"twenty",
		"thirty",
		"forty",
		"fifty",
		"sixty",
		"seventy",
		"eighty",
		"ninety"
	};


	private final String[] _numNames = {
		"zero",
		"one",
		"two",
		"three",
		"four",
		"five",
		"six",
		"seven",
		"eight",
		"nine",
		"ten",
		"eleven",
		"twelve",
		"thirteen",
		"fourteen",
		"fifteen",
		"sixteen",
		"seventeen",
		"eighteen",
		"nineteen"
	};

	private static NumberParser _numberParser = new NumberParser();
	
	private NumberParser(){	
		initializeNumberMap();
	}
	
	protected static NumberParser getInstance(){
		return _numberParser;
	}

	private void initializeNumberMap() {
		initializeWithNumNames();
		initializeWithTensNames();
	}

	private void initializeWithTensNames() {
		for (int i = 1; i < _tensNames.length; i++){
			String key = _tensNames[i];
			Integer value = i * 10;
			_numberMap.put(key, value);
		}
	}

	private void initializeWithNumNames() {
		for (int i = 0; i < _numNames.length; i++){
			String key = _numNames[i];
			Integer value = i;
			_numberMap.put(key, value);
		}
	}

	//this method returns null when error occurs
	protected String parseTheNumbers(String input){
		//cannot reach here if input is null
		assert (input != null);
		
		if (isDigitString(input)){
			return STRING_EMPTY + Integer.parseInt(input);
			//return STRING_EMPTY + Double.parseDouble(input);
		}
		
		String[] numWords = input.split(" ");
		Integer total = null;
		int space = 0;
		boolean isFirstPass = true;
		
		for (int i = numWords.length - 1 ; i >= 0; i--){
			String key = getKey(numWords, i); 
						
			boolean hasSuchNumber = _numberMap.containsKey(key);
			boolean isEmptyString = STRING_EMPTY.equals(key);
			
			if(isEmptyString){
				space++;
				continue;
			}
			
			if (!hasSuchNumber){
				return null;
			}
			
			Integer value = _numberMap.get(key);
			
			if (isFirstPass){
				total = value;
				isFirstPass = false;
			} else {
				total = combineNumbers(numWords, total, i, value, space);
			}
		}
		
		//is either empty string or string with spaces
		if (total == null){
			return null;
		}
		
		return STRING_EMPTY + total;
	}

	protected boolean isDigitString(String input) {
		try{
			Integer.parseInt(input);
			//Double.parseDouble(input);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
	}

	private String getKey(String[] numWords, int pos) {
		String key = numWords[pos];
		key = key.toLowerCase();
		return key;
	}
	
	private Integer combineNumbers(String[] numWords, Integer total, int i,
			Integer value, int space) {
		boolean isNotSingleDigit = singleDigitChecker.compareTo(value) < 0;
		if (isNotSingleDigit){
			total += value;
		} else {
			Integer digitLocation = (int)Math.pow(10, (int) numWords.length - i - 1 - space);
			total += value * digitLocation;
		}
		return total;
	}

	/*testing
	public static void main(String[] args){
		NumberParser parseNumWord = new NumberParser();
		System.out.println(parseNumWord.parseTheNumbers("one")); 
		System.out.println(parseNumWord.parseTheNumbers("ONe"));
		System.out.println(parseNumWord.parseTheNumbers("Twenty one"));
		System.out.println(parseNumWord.parseTheNumbers("One ONe"));
		System.out.println(parseNumWord.parseTheNumbers("One ONe ONE"));
		System.out.println(parseNumWord.parseTheNumbers("One    ONE"));
		
		/*
		 * expected output:
		 * 1
		 * 1
		 * 21
		 * 11
		 * 111
		 * 11
		 *//*
	}
	//*/

}
