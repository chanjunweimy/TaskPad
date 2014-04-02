package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class is for us to find the existence of Date and Time in an input
 * String
 * 
 * Supposed to put all the protected methods from DateAndTimeManager here
 * 
 * @author Lynnette, Jun
 * 
 */

public class DateAndTimeRetriever {

	private static final String STRING_NULL = "null";

	private static final String STRING_EMPTY = "";
	
	private static final String DEADLINE = "DEADLINE";
	private static final String TIME_START = "STARTTIME";
	private static final String TIME_END = "ENDTIME";
	private static final String[] KEYWORD_DEADLINES = {
		"BY",
		"BEFORE",
		"BEF"
	};
	private static final String[] KEYWORD_STARTTIME = {
		"AT",
		"AFTER",
		"ON",
		"IN"
	};
	private static final String[] KEYWORD_ENDTIME = {
		"UNTIL",
		"~"
	};
	
	private static HashMap<String, String> _retrieverMap = new HashMap<String, String>();
	private static DateAndTimeRetriever _retriever = new DateAndTimeRetriever();
	
	private DateAndTimeRetriever(){
		intializeRetrieverMap();
	}
	
	private void intializeRetrieverMap() {
		initializeStartTime();
		initializeEndTime();
		initializeDeadlines();
	}

	private void initializeStartTime() {
		for (String anyStartTime: KEYWORD_STARTTIME){
			_retrieverMap.put(anyStartTime, TIME_START);
		}
	}

	private void initializeEndTime() {
		for (String anyEndTime: KEYWORD_ENDTIME){
			_retrieverMap.put(anyEndTime, TIME_END);
		}
		
	}

	private void initializeDeadlines() {
		for (String anyDeadline: KEYWORD_DEADLINES){
			_retrieverMap.put(anyDeadline, DEADLINE);
		}
	}

	protected static DateAndTimeRetriever getInstance(){
		return _retriever;
	}
	
	/**
	 * In an input string, check if there is valid time
	 * 
	 * @param inputString
	 * @return time
	 */
	protected TimeObject findTime(String inputString) {
		TimeObject timeObject = null;

		String parsedTime = isValidTime(inputString);
		if (isNotEmptyParsedString(parsedTime)) {
			timeObject = createNewTimeObject(parsedTime, inputString);
		}
		return timeObject;
	}

	/* Helper methods for checking valid time in a String */
	private String isValidTime(String input) {
		input = trimInput(input);
		try {
			return TimeParser.parseTimeInput(input);
		} catch (TimeErrorException | InvalidTimeException e) {
			return STRING_EMPTY;
		}
	}

	private TimeObject createNewTimeObject(String parsedTime,
			String inputTime) {
		return new TimeObject(parsedTime.trim(), inputTime.trim());
	}

	/**
	 * In an input string, check if there is valid date
	 * 
	 * @param inputString
	 * @return date
	 */
	protected DateObject findDate(String inputString) {
		DateObject dateObject = null;

		String parsedDate = isValidDate(inputString);
		if (isNotEmptyParsedString(parsedDate)) {
			dateObject = createDateObject(parsedDate, inputString);
		}
		return dateObject;
	}

	/* Helper method for checking valid date in a String */
	private String isValidDate(String input) {
		input = trimInput(input);
		DateParser dateParser = DateParser.getInstance();
		try {
			return dateParser.parseDate(input);
		} catch (InvalidDateException e) {
			return STRING_EMPTY;
		}
	}

	private String trimInput(String input) {
		input = input.trim();
		return input;
	}

	private DateObject createDateObject(String parsedDate, String input) {
		return new DateObject(parsedDate, input.trim());
	}

	private boolean isNotEmptyParsedString(String parsedString) {
		return !parsedString.equals(STRING_EMPTY);
	}

	
	/**
	 * 
	 * @param desc
	 * @return desc |Deadline: | StartTime: Date then Time | EndTime: Date Then Time
	 */
	protected String formatDateAndTimeInString(String desc) {
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		String todayDate = datm.getTodayDate();
		String now = datm.getTodayTime();
		
		String deadlineDate = null;
		String deadlineTime = null;
		String startDate = null;
		String startTime = null;
		String endDate = null;
		String endTime = null;
		
		// split all nonAlphaNumerics character
		String alphaNumericSpaceDesc = getAlphaNumericSpaceDesc(desc);

		// step one: convert all number words to numbers using number parser
		String numberedInput = parseNumber(alphaNumericSpaceDesc);

		// step two: find holiday words and replace with date
		String holidayString = parseHolidayDates(numberedInput);

		// step three: find dayParser words and find words before (i.e.
		// next/prev) and replace with date
		String dayString = parseDay(holidayString);

		// step four: find dates -- find month words & find number before and
		// after
		String dateString = parseDate(dayString);

		// step five: find PM or AM words and find time unit before and replace
		// with time
		String timeString = parseTime(dateString);
		
		String[] flexiTokens = timeString.split(" ");

		String type = null;
		String recordDate = null;
		String recordTime = null;
		for (String token: flexiTokens){
			String value = _retrieverMap.get(token);
			if (value != null){
				type = value;
				recordDate = null;
				recordTime = null;
			} else {
				if (type != null){
					boolean hasDone = false;
					if (isDate(token)){
						if (recordDate == null){
							recordDate = token;
							hasDone = true;
						} 
					} else if (isTime(token)){
						if (recordDate == null){
							recordTime = token;
							hasDone = true;
						} 
					}
					
					if (!hasDone){
						if (DEADLINE.equals(type)){
							if (deadlineDate == null){
								deadlineDate = recordDate;
							} else {
								
							}
						} else if (TIME_START.equals(type)){
							
						} else if (TIME_END.equals(type)){
							
						} else {
							//unreachable
							assert (false);
						}
						
						
					}
					
				}
			}
		}

		// return that string to parse in respective Add/Addrem/Alarm classes -
		// already done with return input
		String deadlineRes = deadlineDate + " " + deadlineTime;
		String startTimeRes = startDate + " " + startTime;
		String endTimeRes = endDate + " " + endTime;
		return desc + " " + deadlineRes + " " + startTimeRes + " " + endTimeRes;
	}

	private int compareDate(String firstDateString, String secondDateString){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date firstDate = new Date();
		Date secondDate = new Date();
		try {
			firstDate = sdf.parse(firstDateString);
			secondDate = sdf.parse(secondDateString);
		} catch (ParseException e) {
			//should not use this function if it hasn't been converted
			assert (false);
		}
		return firstDate.compareTo(secondDate);
	}
	
	private String parseTime(String dateString) {
		String[] timeTokens = dateString.split(" ");
		StringBuffer timeString = new StringBuffer();
		boolean[] isModified = new boolean[timeTokens.length];

		initializeArray(isModified);

		int maxJoinWord = 4;
		for (int i = maxJoinWord; i >= 1; i--) {
			changeNTimeWords(timeTokens, isModified, i);
		}
		timeString = buildString(timeTokens, timeString);

		return timeString.toString().trim();
	}

	/**
	 * @param dateTokens
	 * @param isModified
	 */
	private void changeNTimeWords(String[] timeTokens,
			boolean[] isModified, int n) {
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		for (int i = n - 1; i < timeTokens.length; i++) {
			String token = timeTokens[i];
			StringBuffer wholeString = new StringBuffer();
			String timeInput;

			// combine n words:
			if (allNotModified(isModified, i, n)) {
				for (int j = i - n + 1; j <= i - 1; j++) {
					wholeString.append(timeTokens[j]);
				}
				wholeString.append(token);
				timeInput = wholeString.toString().trim();
				// System.err.println(dateInput);
				if (isTime(timeInput)) {
				
					try {
						timeInput = datm.parseTimeInput(timeInput);
					} catch (TimeErrorException | InvalidTimeException e) {
						assert (false);
					}
					
					allNWordsModified(isModified, i, n);
					timeTokens[i] = timeInput;
					
					
					for (int j = i - n + 1; j <= i - 1; j++) {
						timeTokens[j] = null;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param dayString
	 * @return
	 */
	private String parseDate(String dayString) {
		String[] dateTokens = dayString.split(" ");
		StringBuffer dateString = new StringBuffer();
		boolean[] isModified = new boolean[dateTokens.length];

		initializeArray(isModified);

		int maxJoinWord = 5;
		for (int i = maxJoinWord; i >= 1; i--) {
			changeNDateWords(dateTokens, isModified, i);
		}
		dateString = buildString(dateTokens, dateString);

		return dateString.toString().trim();
	}

	/**
	 * @param dateTokens
	 * @param isModified
	 */
	private void changeNDateWords(String[] dateTokens,
			boolean[] isModified, int n) {
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		for (int i = n - 1; i < dateTokens.length; i++) {
			String token = dateTokens[i];
			StringBuffer wholeString = new StringBuffer();
			String dateInput;

			// combine n words:
			if (allNotModified(isModified, i, n)) {
				for (int j = i - n + 1; j <= i - 1; j++) {
					wholeString.append(dateTokens[j]);
				}
				wholeString.append(token);
				dateInput = wholeString.toString().trim();
				// System.err.println(dateInput);
				if (isDate(dateInput)) {
					try {
						dateInput = datm.parseDate(dateInput);
					} catch (InvalidDateException e) {
						assert (false);
					} 
					allNWordsModified(isModified, i, n);
					dateTokens[i] = dateInput;
					for (int j = i - n + 1; j <= i - 1; j++) {
						dateTokens[j] = null;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param isModified
	 * @param idx
	 * @param num
	 */
	private void allNWordsModified(boolean[] isModified, int idx, int num) {
		for (int i = idx; i >= idx - num + 1; i--) {
			isModified[i] = true;
		}
	}

	/**
	 * @param isModified
	 * @param i
	 * @return
	 */
	private boolean allNotModified(boolean[] isModified, int idx, int num) {
		for (int i = idx; i >= idx - num + 1; i--) {
			if (isModified[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param input
	 */
	private String getAlphaNumericSpaceDesc(String input) {
		Scanner sc = new Scanner(input);
		StringBuffer alphaNumericSpaceString = new StringBuffer();
		while (sc.hasNext()) {
			String token = sc.next();
			token = splitNonAlphaNumericCharacter(token);
			alphaNumericSpaceString.append(token + " ");
		}
		sc.close();
		return alphaNumericSpaceString.toString().trim();
	}

	/**
	 * @param token
	 */
	private String splitNonAlphaNumericCharacter(String token) {
		// characters in date and time cannot be omitted
		if (isDate(token) || isTime(token)) {
			return token;
		}

		// token = token.replaceAll("!", "");
		// token = token.replaceAll(".", "");
		// token = token.replaceAll(",", " ");
		// token = token.replaceAll(";", " ");
		// token = token.replaceAll("?", "");
		// token = token.replaceAll("\"", " \" ");
		// token = token.replaceAll("\'", " \' ");
		// token = token.replaceAll("(", "");
		// token = token.replaceAll(")", "");
		// token = token.replaceAll("~", " until ");
		// token = token.replaceAll("*", "");

		Scanner sc = new Scanner(token);
		// sc.useDelimiter("[^A-Za-z0-9]");
		StringBuffer tokenBuilder = new StringBuffer();
		String anyCharacter;
		while ((anyCharacter = sc.findInLine("[^A-Za-z0-9]")) != null) {
			int splitIndex = token.indexOf(anyCharacter);
			String tempTokens = token.substring(0, splitIndex);
			token = token.substring(splitIndex + 1, token.length());
			tokenBuilder.append(tempTokens + " " + anyCharacter + " ");
		}
		if (token != null) {
			tokenBuilder.append(token);
		}
		sc.close();
		return tokenBuilder.toString().trim();
	}

	private boolean isTime(String input) {
		input = trimInput(input);
		try {
			TimeParser.parseTimeInput(input);
		} catch (TimeErrorException | InvalidTimeException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean isDate(String input) {
		input = trimInput(input);
		DateParser dateParser = DateParser.getInstance();
		try {
			dateParser.parseDate(input);
		} catch (InvalidDateException e) {
			return false;
		}
		return true;
	}

	/**
	 * @param holidayString
	 */
	private String parseDay(String holidayString) {
		String[] dayTokens = holidayString.split(" ");
		StringBuffer dayString = new StringBuffer();
		SpecialWordParser swp = SpecialWordParser.getInstance();
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		DayParser dp = DayParser.getInstance();
		boolean[] isModified = new boolean[dayTokens.length];

		initializeArray(isModified);

		for (int i = 0; i < dayTokens.length; i++) {
			String firstToken = dayTokens[i];
			StringBuffer changedTokens = new StringBuffer();

			if (dp.isDay(firstToken)) {
				isModified[i] = true;
				for (int j = i - 1; j >= 0; j--) {
					if (isModified[j]) {
						break;
					}

					isModified[j] = true;
					String token = dayTokens[j];
					if (swp.isSpecialWord(token)) {
						changedTokens.append(token + " ");
						dayTokens[j] = null;
						isModified[j] = true;
					} else {
						break;
					}
				}
				changedTokens.append(firstToken);

				try {
					// System.err.println(changedTokens.toString());
					dayTokens[i] = datm.parseDayToDate(changedTokens.toString()
							.trim());
				} catch (InvalidDayException | DatePassedException e) {
					assert (false);
				}
			}
		}

		dayString = buildString(dayTokens, dayString);

		return dayString.toString().trim();
	}

	/**
	 * 
	 * @param numberedInput
	 * @return
	 */
	private String parseHolidayDates(String numberedInput) {
		String[] numberInputTokens = numberedInput.split(" ");
		boolean[] isModified = new boolean[numberInputTokens.length];
		StringBuffer holidayString = new StringBuffer();

		initializeArray(isModified);

		HolidayDates holidayParser = HolidayDates.getInstance();
		for (int i = 2; i < numberInputTokens.length; i++) {
			String token = numberInputTokens[i];

			String holidayInput;
			String pastOneToken, pastTwoToken;

			// search 3 words:
			if (allNotModified(isModified, i, 3)) {
				pastOneToken = numberInputTokens[i - 1];
				pastTwoToken = numberInputTokens[i - 2];
				holidayInput = holidayParser.replaceHolidayDate(pastTwoToken
						+ " " + pastOneToken + " " + token);
				if (holidayInput != null) {
					numberInputTokens[i] = holidayInput;
					numberInputTokens[i - 1] = null;
					numberInputTokens[i - 2] = null;
					isModified[i] = true;
					isModified[i - 1] = true;
					isModified[i - 2] = true;
					// holidayString.append(holidayInput + " ");
				}
			}
		}

		for (int i = 1; i < numberInputTokens.length; i++) {
			String token = numberInputTokens[i];
			String holidayInput;
			String pastOneToken;

			// search 2 words:
			if (allNotModified(isModified, i, 2)) {
				pastOneToken = numberInputTokens[i - 1];
				holidayInput = holidayParser.replaceHolidayDate(pastOneToken
						+ " " + token);
				if (holidayInput != null) {
					numberInputTokens[i] = holidayInput;
					numberInputTokens[i - 1] = null;
					isModified[i] = true;
					isModified[i - 1] = true;
					// holidayString.append(holidayInput + " ");
				}
			}
		}

		for (int i = 0; i < numberInputTokens.length; i++) {
			String token = numberInputTokens[i];

			if (isModified[i]) {
				continue;
			}
			String holidayInput;
			// search 1 word
			holidayInput = holidayParser.replaceHolidayDate(token);
			if (holidayInput != null) {
				numberInputTokens[i] = holidayInput;
				isModified[i] = true;
				// holidayString.append(holidayInput + " ");
			}
		}

		buildString(numberInputTokens, holidayString);

		return holidayString.toString().trim();
	}

	/**
	 * @param numberInputTokens
	 * @param holidayString
	 */
	private StringBuffer buildString(String[] anyTokens,
			StringBuffer anyString) {
		for (String token : anyTokens) {
			if (token != null) {
				anyString.append(token + " ");
			}
		}
		return anyString;
	}

	/**
	 * @param isModified
	 */
	private void initializeArray(boolean[] isModified) {
		for (int i = 0; i < isModified.length; i++) {
			isModified[i] = false;
		}
	}

	/**
	 * @param input
	 * @param datmParser
	 */
	private String parseNumber(String input) {
		DateAndTimeManager datmParser = DateAndTimeManager.getInstance();
		Scanner sc = new Scanner(input);
		StringBuffer changedString = new StringBuffer();
		StringBuffer numberString = new StringBuffer();
		boolean isNumberContinue = false;
		while (sc.hasNext()) {
			String token = sc.next();
			if (!datmParser.isNumber(token)) {
				if (isNumberContinue) {
					String realNumber = datmParser.parseNumber(numberString
							.toString().trim());
					changedString.append(realNumber + " ");
					numberString = new StringBuffer();
				}

				changedString.append(token + " ");
				isNumberContinue = false;
			} else {
				if (!isNumberContinue) {
					isNumberContinue = true;
				}
				numberString.append(token + " ");
			}
		}

		String realNumber = datmParser.parseNumber(numberString.toString()
				.trim());
		if (realNumber != null) {
			changedString.append(realNumber + " ");
		}

		sc.close();
		return changedString.toString().trim();
	}

	/**
	 * @deprecated
	 * @param input
	 */
	@SuppressWarnings("unused")
	private String createDesc(String input) {
		String desc = input.trim();

		if (!desc.startsWith("\"")) {
			desc = "\"" + desc;
		}

		if (!input.endsWith("\"")) {
			desc = desc + "\"";
		}

		return desc;
	}

	public static void main(String[] args) {
		DateAndTimeRetriever datr = DateAndTimeRetriever.getInstance();
		System.out
				.println(datr.getAlphaNumericSpaceDesc("I am looking for Lynnette. She is going home on Monday."));
		System.out
				.println(datr.getAlphaNumericSpaceDesc("I am looking for Lynnette. She is going home on 1/4/15 12:00."));
		System.out.println(datr.parseNumber("one one one aaa one one one"));
		System.out.println(datr.parseNumber("one one one aaa"));
		System.out.println(datr.parseNumber("aaa"));
		System.out
				.println(datr.parseHolidayDates("last Christmas I gave you my heart Christmas"));
		System.out
				.println(datr.parseHolidayDates("last New Year I gave you my heart Christmas"));
		System.out
				.println(datr.parseHolidayDates("last April Fool Day I gave you my heart Christmas"));
		System.out.println(datr.parseDay("Monday I want to eat Monday"));
		System.out
				.println(datr.parseDay("next nxt NXT prev Monday I want to catch Pokemon!"));
		System.out
				.println(datr.parseDay("next ASH nxt Monday I want to catch Pokemon nxt Fri !"));
		System.out
				.println(datr.parseDate("1 / 11 / 2014 , I watch movie in 1 December"));
		System.out.println(datr.parseTime("1 am"));
	}

}
