//@author A0112084U

package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * This class is for us to find the existence of Date and Time in an input
 * String
 * 
 * Supposed to put all the protected methods from DateAndTimeManager here
 * 
 */

public class DateAndTimeRetriever {
	private final static Logger LOGGER = Logger.getLogger("TaskPad");

	private static final int POSITION_DESCRIPTION = 3;
	private static final int POSITION_ENDTIME = 2;
	private static final int POSITION_STARTTIME = 1;
	private static final int POSITION_DEADLINE = 0;

	private static final String STRING_NULL = "null";

	private static final String STRING_EMPTY = "";

	private static final String DEADLINE = "DEADLINE";
	private static final String TIME_START = "STARTTIME";
	private static final String TIME_END = "ENDTIME";
	private static final String[] KEYWORD_DEADLINES = { "BY", "BEFORE", "BEF" };
	private static final String[] KEYWORD_STARTTIME = { "AT", "AFTER", "ON",
			"IN", "FROM", "FRO" };
	private static final String[] KEYWORD_ENDTIME = { "UNTIL", "TILL", "TO",
			"TIL", "~", "FOR" };

	private static HashMap<String, String> _retrieverMap = new HashMap<String, String>();
	private static DateAndTimeRetriever _retriever = new DateAndTimeRetriever();

	/**
	 * @deprecated
	 */
	private static final String[] KEYWORD_TODAY = { "TODAY", "TDY", "2DAY" };

	/**
	 * @deprecated
	 */
	private static final String[] KEYWORD_NOW = { "NOW" };

	private DateAndTimeRetriever() {
		intializeRetrieverMap();
	}

	private void intializeRetrieverMap() {
		initializeStartTime();
		initializeEndTime();
		initializeDeadlines();
	}

	private void initializeStartTime() {
		for (String anyStartTime : KEYWORD_STARTTIME) {
			_retrieverMap.put(anyStartTime, TIME_START);
		}
	}

	private void initializeEndTime() {
		for (String anyEndTime : KEYWORD_ENDTIME) {
			_retrieverMap.put(anyEndTime, TIME_END);
		}

	}

	private void initializeDeadlines() {
		for (String anyDeadline : KEYWORD_DEADLINES) {
			_retrieverMap.put(anyDeadline, DEADLINE);
		}
	}

	protected static DateAndTimeRetriever getInstance() {
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
		TimeParser tp = TimeParser.getInstance();
		try {
			return tp.parseTimeInput(input);
		} catch (TimeErrorException | InvalidTimeException e) {
			return STRING_EMPTY;
		}
	}

	private TimeObject createNewTimeObject(String parsedTime, String inputTime) {
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

	protected ArrayList<String> searchTimeAndDate(String desc)
			throws InvalidQuotesException {
		String formattedString = convertStandardDateAndTime(desc);

		// System.err.println(formattedString);

		ArrayList<String> searchResult = extractAllDateAndTime(formattedString);

		return searchResult;
	}

	/**
	 * @param formattedString
	 */
	private ArrayList<String> extractAllDateAndTime(String formattedString) {
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		String todayDate = datm.getTodayDate();

		String recordTime = null;
		String recordDate = null;

		ArrayList<String> TimeAndDateRes = new ArrayList<String>();

		String[] formattedTokens = formattedString.split(" ");
		for (int i = 0; i < formattedTokens.length; i++) {
			String token = formattedTokens[i];
			if (isDate(token)) {
				if (recordDate != null) {
					String res;
					if (recordTime == null) {
						res = recordDate;
						recordDate = null;
					} else {
						res = recordTime + " " + recordDate;
						recordDate = null;
						recordTime = null;
					}
					TimeAndDateRes.add(res);
				}
				recordDate = token;
			} else if (isTime(token)) {
				if (recordTime != null) {
					String res;
					if (recordDate == null) {
						res = recordTime + " " + todayDate;
						recordTime = null;
					} else {
						res = recordTime + " " + recordDate;
						recordDate = null;
						recordTime = null;
					}
					TimeAndDateRes.add(res);
				}
				recordTime = token;
			}
		}

		String res = null;
		if (recordTime != null && recordDate != null) {
			res = recordTime + " " + recordDate;
		} else if (recordTime != null && recordDate == null) {
			res = recordTime + " " + todayDate;
		} else if (recordTime == null && recordDate != null) {
			res = recordDate;
		}

		if (res != null) {
			TimeAndDateRes.add(res);
		}
		return TimeAndDateRes;
	}

	/**
	 * format all the date and time in a string to standard format
	 * 
	 * @param desc
	 * @throws InvalidQuotesException
	 */
	private String convertStandardDateAndTime(String desc)
			throws InvalidQuotesException {
		String alphaNumericSpaceDesc = getAlphaNumericSpaceDesc(desc);

		LOGGER.info("alphaNumericSpaceDesc : " + alphaNumericSpaceDesc);

		String noQuoteDesc = removeParseFreeZone(alphaNumericSpaceDesc);

		LOGGER.info("noQuoteDesc : " + noQuoteDesc);

		String numberedInput = parseNumber(noQuoteDesc);

		LOGGER.info("numberedInput : " + numberedInput);

		String dateString = parseDate(numberedInput);

		LOGGER.info("dateString : " + dateString);

		String holidayString = parseHolidayDates(dateString);

		LOGGER.info("holidayString : " + holidayString);

		String dayString = parseDay(holidayString);

		LOGGER.info("dayString : " + dayString);

		String timeString = parseTime(dayString);
		LOGGER.info("timeString: " + timeString);

		String timeWordString = parseTimeWord(timeString);

		timeWordString = timeWordString.replaceAll("\"", "").trim();

		LOGGER.info("timeWordString: " + timeWordString);

		String todayAndNowString = timeWordString;
		// String todayAndNowString = parseTodayAndNow(timeWordString);

		LOGGER.info("todayAndNowString : " + todayAndNowString);

		return todayAndNowString;
	}

	/**
	 * format DateAndTime as Deadline, StartTime, EndTime
	 * 
	 * @param desc
	 * @return desc | Deadline: | StartTime: Date then Time | EndTime: Date Then
	 *         Time
	 * @throws InvalidQuotesException
	 */
	protected String formatDateAndTimeInString(String desc)
			throws InvalidQuotesException {

		String formattedString = convertStandardDateAndTime(desc);

		// System.err.println(formattedString);

		ArrayList<String> allDateAndTime = extractDateAndTime(formattedString);

		// System.err.println(allDateAndTime.get(POSITION_DEADLINE));
		// System.err.println(allDateAndTime.get(POSITION_STARTTIME));
		// System.err.println(allDateAndTime.get(POSITION_ENDTIME));

		desc = allDateAndTime.remove(POSITION_DESCRIPTION);

		allDateAndTime = modifyAllDateAndTime(allDateAndTime);

		String deadlineRes = allDateAndTime.get(POSITION_DEADLINE);
		String startTimeRes = allDateAndTime.get(POSITION_STARTTIME);
		String endTimeRes = allDateAndTime.get(POSITION_ENDTIME);

		// return that string to parse in respective Add/Addrem/Alarm classes -
		// already done with return input

		return desc + " " + deadlineRes + " " + startTimeRes + " " + endTimeRes;
	}

	/**
	 * @param timeString
	 * @return
	 */
	private String parseTimeWord(String timeString) {
		String[] timeWordTokens = timeString.split(" ");

		TimeWordParser twp = TimeWordParser.getInstance();
		SpecialWordParser swp = SpecialWordParser.getInstance();
		NumberParser np = NumberParser.getInstance();

		boolean[] isModified = new boolean[timeWordTokens.length];

		initializeArray(isModified);

		for (int i = 0; i < timeWordTokens.length; i++) {
			String firstToken = timeWordTokens[i];
			StringBuffer changedTokens = new StringBuffer();

			if (twp.isTimeUnits(firstToken)) {
				isModified[i] = true;
				String secondToken = null;

				for (int j = i - 1; j >= 0; j--) {
					if (isModified[j]) {
						break;
					}

					isModified[j] = true;
					String token = timeWordTokens[j];

					if (swp.isSpecialWord(token)) {
						changedTokens.append(token + " ");
						timeWordTokens[j] = null;
					} else if (j == i - 1 && np.isDigitString(token)) {
						timeWordTokens[j] = null;
						secondToken = token;
					} else {
						break;
					}

				}
				if (secondToken != null) {
					changedTokens.append(secondToken + " ");
				}
				changedTokens.append(firstToken);

				try {
					timeWordTokens[i] = twp
							.parseTimeWordWithSpecialWord(changedTokens
									.toString().trim());
				} catch (NullTimeUnitException | NullTimeValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		StringBuffer timeWordString = new StringBuffer();
		timeWordString = buildString(timeWordTokens, timeWordString);

		return timeWordString.toString().trim();
	}

	private String removeParseFreeZone(String alphaNumericSpaceDesc)
			throws InvalidQuotesException {
		boolean removeStat = false;
		String[] descTokens = alphaNumericSpaceDesc.split(" ");
		for (int i = 0; i < descTokens.length; i++) {
			// don't allow ' ' for parse free zone because user might type it
			// who's

			if ("\"".equals(descTokens[i])) {
				if (!removeStat) {
					removeStat = true;
				} else {
					removeStat = false;
				}
				descTokens[i] = null;
				// descTokens[i] = "\"" + descTokens[i];
			} else {
				if (removeStat) {
					// descTokens[i] = null;
					descTokens[i] = "\"" + descTokens[i];
				}
			}
		}

		if (removeStat) {
			throw new InvalidQuotesException();
		}

		StringBuffer tokensBuilder = new StringBuffer();
		tokensBuilder = buildString(descTokens, tokensBuilder);

		return tokensBuilder.toString().trim();
	}

	private boolean isParseFree(String token) {
		if (token == null) {
			return false;
		}
		return token.startsWith("\"");
	}

	/**
	 * @param allDateAndTime
	 */
	private ArrayList<String> modifyAllDateAndTime(
			ArrayList<String> allDateAndTime) {
		for (int i = 0; i < allDateAndTime.size(); i++) {
			if (allDateAndTime.get(i) == null
					|| allDateAndTime.get(i).trim().isEmpty()) {
				allDateAndTime.set(i, STRING_NULL + " " + STRING_NULL);
			} else if (allDateAndTime.get(i).split(" ").length == 1) {
				String element = allDateAndTime.get(i);
				allDateAndTime.set(i, element + " " + STRING_NULL);
			}
		}
		return allDateAndTime;
	}

	/**
	 * @param timeString
	 */
	private ArrayList<String> extractDateAndTime(String timeString) {
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		String todayDate = datm.getTodayDate();
		String now = datm.getTodayDateAndTime();

		String[] flexiTokens = timeString.split(" ");

		String startDateEarliest = null;
		String startTimeEarliest = null;
		String startEarliest = null;

		LinkedList<String> startDates = new LinkedList<String>();
		LinkedList<String> startTimes = new LinkedList<String>();

		LinkedList<String> deadlineDates = new LinkedList<String>();
		LinkedList<String> deadlineTimes = new LinkedList<String>();

		LinkedList<String> endDates = new LinkedList<String>();
		LinkedList<String> endTimes = new LinkedList<String>();

		ArrayList<String> allDateAndTime = new ArrayList<String>();

		StringBuffer descBuilder = new StringBuffer();
		String desc;

		String recordDate = null;
		String recordTime = null;

		for (int i = flexiTokens.length - 1; i >= 0; i--) {
			String token = flexiTokens[i];

			if (isParseFree(token)) {
				if (recordDate != null || recordTime != null) {
					startDates.add(recordDate);
					startTimes.add(recordTime);

					recordDate = null;
					recordTime = null;
				}
			} else if (isDate(token)) {
				token = parseDate(token);

				if (recordDate == null) {
					recordDate = token;
				} else {
					startDates.add(recordDate);
					startTimes.add(recordTime);

					recordDate = token;
					recordTime = null;
				}

				flexiTokens[i] = null;

			} else if (isTime(token)) {
				token = parseTime(token);

				if (recordTime == null) {
					recordTime = token;
				} else {
					startDates.add(recordDate);
					startTimes.add(recordTime);

					recordDate = null;
					recordTime = token;
				}

				flexiTokens[i] = null;

			} else if (isType(token)) {
				boolean useWrong = (recordDate == null && recordTime == null);

				if (!useWrong) {
					token = token.toUpperCase();
					String type = _retrieverMap.get(token);
					if (DEADLINE.equals(type)) {
						deadlineDates.add(recordDate);
						deadlineTimes.add(recordTime);
					} else if (TIME_START.equals(type)) {
						startDates.add(recordDate);
						startTimes.add(recordTime);
					} else if (TIME_END.equals(type)) {
						endDates.add(recordDate);
						endTimes.add(recordTime);
					}
					recordDate = null;
					recordTime = null;

					flexiTokens[i] = null;

				}
			} else {
				if (recordDate != null || recordTime != null) {
					startDates.add(recordDate);
					startTimes.add(recordTime);

					recordDate = null;
					recordTime = null;
				}
			}
		}

		// System.err.println(timeString + "\n" + startDates.toString());

		if (recordDate != null || recordTime != null) {
			startDates.add(recordDate);
			startTimes.add(recordTime);
		}

		startEarliest = retrieveStartEarliest(todayDate, now,
				startDateEarliest, startTimeEarliest, startEarliest,
				startDates, startTimes);

		if (startEarliest == null) {
			startDateEarliest = todayDate;
		} else {
			startDateEarliest = startEarliest.split(" ")[0];
		}

		// LOGGER.info(": " + startDateEarliest);

		String deadlineLatest = retrieveNotStartLatest(deadlineDates,
				deadlineTimes, startDateEarliest);
		String endLatest = retrieveNotStartLatest(endDates, endTimes,
				startDateEarliest);

		// System.err.println(deadlineLatest);
		// System.err.println(startEarliest);
		// System.err.println(endLatest);

		if (endLatest != null && startEarliest != null
				&& compareDateAndTime(endLatest, startEarliest) <= 0) {
			endLatest = null;
		} else if (endLatest != null && compareDateAndTime(endLatest, now) <= 0) {
			endLatest = null;
		}

		// System.err.println(deadlineLatest);
		// System.err.println(startEarliest);
		if (deadlineLatest != null && startEarliest != null
				&& compareDateAndTime(deadlineLatest, startEarliest) <= 0) {
			deadlineLatest = null;
		} else if (deadlineLatest != null
				&& compareDateAndTime(deadlineLatest, now) <= 0) {
			deadlineLatest = null;
		}

		descBuilder = buildString(flexiTokens, descBuilder);
		desc = descBuilder.toString().trim();

		allDateAndTime.add(deadlineLatest);
		allDateAndTime.add(startEarliest);
		allDateAndTime.add(endLatest);
		allDateAndTime.add(desc);

		return allDateAndTime;
	}

	private boolean isToday(String input) {
		input = input.toUpperCase();
		for (int i = 0; i < KEYWORD_TODAY.length; i++) {
			if (KEYWORD_TODAY[i].equals(input)) {
				return true;
			}
		}
		return false;
	}

	private boolean isNow(String input) {
		input = input.toUpperCase();
		for (int i = 0; i < KEYWORD_NOW.length; i++) {
			if (KEYWORD_NOW[i].equals(input)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param todayDate
	 * @param now
	 * @param startDateEarliest
	 * @param startTimeEarliest
	 * @param startEarliest
	 * @param startDates
	 * @param startTimes
	 * @return
	 */
	private String retrieveStartEarliest(String todayDate, String now,
			String startDateEarliest, String startTimeEarliest,
			String startEarliest, LinkedList<String> startDates,
			LinkedList<String> startTimes) {
		assert (startDates.size() == startTimes.size());

		for (int i = 0; i < startDates.size(); i++) {
			if (startDates.get(i) != null && startTimes.get(i) != null) {
				String start = startDates.get(i) + " " + startTimes.get(i);
				if (startEarliest == null
						|| compareDateAndTime(startEarliest, start) > 0) {
					startEarliest = start;
				}
			} else if (startDates.get(i) != null && startTimes.get(i) == null) {
				String startDate = startDates.get(i);
				if (startDateEarliest == null
						|| compareDate(startDateEarliest, startDate) > 0) {
					startDateEarliest = startDate;
				}
			} else if (startDates.get(i) == null && startTimes.get(i) != null) {
				String startTime = startTimes.get(i);
				if (startTimeEarliest == null
						|| compareTime(startTimeEarliest, startTime) > 0) {
					startTimeEarliest = startTime;
				}
			} else {
				// unreachable
				assert (false);
			}
		}

		if (startDateEarliest != null) {
			if (startTimeEarliest == null) {
				startTimeEarliest = "00:00";
			}

			String start = startDateEarliest + " " + startTimeEarliest;
			if (startEarliest == null
					|| compareDateAndTime(startEarliest, start) > 0) {
				startEarliest = start;
			}
		} else if (startTimeEarliest != null) {
			String start = todayDate + " " + startTimeEarliest;
			if (startEarliest == null
					|| compareDateAndTime(startEarliest, start) > 0) {
				startEarliest = start;
			}
		} else if (startEarliest == null) {
			// startEarliest = now;
			return null;
		}
		return startEarliest.trim();
	}

	private String retrieveNotStartLatest(LinkedList<String> Dates,
			LinkedList<String> Times, String startDateEarliest) {
		assert (Dates.size() == Times.size());
		String latest = null;
		String dateLatest = null;
		String timeLatest = null;

		for (int i = 0; i < Dates.size(); i++) {
			if (Dates.get(i) != null && Times.get(i) != null) {
				String notStart = Dates.get(i) + " " + Times.get(i);
				if (latest == null || compareDateAndTime(latest, notStart) < 0) {
					latest = notStart;
				}
			} else if (Dates.get(i) != null && Times.get(i) == null) {
				String dateGet = Dates.get(i);
				if (dateLatest == null || compareDate(dateLatest, dateGet) < 0) {
					dateLatest = dateGet;
				}
			} else if (Dates.get(i) == null && Times.get(i) != null) {
				String timeGet = Times.get(i);
				if (timeLatest == null || compareTime(timeLatest, timeGet) < 0) {
					timeLatest = timeGet;
				}
			} else {
				// unreachable
				assert (false);
			}
		}

		if (dateLatest != null) {
			if (timeLatest == null) {
				timeLatest = "23:59";
			}
			String cur = dateLatest + " " + timeLatest;
			if (latest == null || compareDateAndTime(latest, cur) < 0) {
				latest = cur;
			}
		} else if (timeLatest != null) {
			String cur = startDateEarliest + " " + timeLatest;
			if (latest == null || compareDateAndTime(latest, cur) < 0) {
				latest = cur;
			}
		}
		if (latest != null) {
			latest = latest.trim();
		}

		return latest;
	}

	private boolean isType(String token) {
		return _retrieverMap.containsKey(token.toUpperCase());
	}

	protected int compareDate(String firstDateString, String secondDateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date firstDate = new Date();
		Date secondDate = new Date();
		try {
			firstDate = sdf.parse(firstDateString);
			secondDate = sdf.parse(secondDateString);
		} catch (ParseException e) {
			// should not use this function if it hasn't been converted
			assert (false);
		}
		return firstDate.compareTo(secondDate);
	}

	protected int compareTime(String firstTimeString, String secondTimeString) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date firstTime = new Date();
		Date secondTime = new Date();
		try {
			firstTime = sdf.parse(firstTimeString);
			secondTime = sdf.parse(secondTimeString);
		} catch (ParseException e) {
			// should not use this function if it hasn't been converted
			assert (false);
		}
		return firstTime.compareTo(secondTime);
	}

	protected int compareDateAndTime(String firstDateString,
			String secondDateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date firstDate = new Date();
		Date secondDate = new Date();
		try {
			firstDate = sdf.parse(firstDateString);
			secondDate = sdf.parse(secondDateString);
		} catch (ParseException e) {
			// should not use this function if it hasn't been converted
			assert (false);
		}
		return firstDate.compareTo(secondDate);
	}

	protected int compareDateAndTimeExecutor(String firstDateString,
			String secondDateString) {
		if (firstDateString == null || secondDateString == null) {
			return -2;
		}

		firstDateString = firstDateString.trim();
		secondDateString = secondDateString.trim();

		firstDateString = addTimeCap(firstDateString);
		secondDateString = addTimeCap(secondDateString);

		if (firstDateString == null || secondDateString == null) {
			return -2;
		}

		return compareDateAndTime(firstDateString, secondDateString);
	}

	/**
	 * @param firstDateString
	 * @return
	 */
	private String addTimeCap(String dateString) {
		if (!isDateAndTime(dateString)) {
			if (isDate(dateString)) {
				dateString = dateString + " 00:00";
			} else if (isTimeAndDate(dateString)) {
				String[] tokens = dateString.split(" ");
				dateString = tokens[1] + " " + tokens[0];
			} else {
				dateString = null;
			}
		}
		return dateString;
	}

	private boolean isTimeAndDate(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");

		try {
			sdf.parse(dateString);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	private boolean isDateAndTime(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		try {
			sdf.parse(dateString);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	private String parseTime(String dateString) {
		String[] timeTokens = dateString.split(" ");
		StringBuffer timeString = new StringBuffer();
		boolean[] isModified = new boolean[timeTokens.length];

		int maxJoinWord = 4;
		for (int i = maxJoinWord; i >= 1; i--) {
			initializeArray(isModified);
			changeNTimeWords(timeTokens, isModified, i);
		}
		timeString = buildString(timeTokens, timeString);

		return timeString.toString().trim();
	}

	/**
	 * @param dateTokens
	 * @param isModified
	 */
	private void changeNTimeWords(String[] timeTokens, boolean[] isModified,
			int n) {
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		NumberParser np = NumberParser.getInstance();
		for (int i = n - 1; i < timeTokens.length; i++) {
			String token = timeTokens[i];
			StringBuffer wholeString = new StringBuffer();
			String timeInput;

			if (isParseFree(token)) {
				isModified[i] = true;
				continue;
			}

			// combine n words:
			if (allNotModified(isModified, i, n)) {
				boolean twoNumTgt = false;
				for (int j = i - n + 1; j <= i - 1; j++) {
					wholeString.append(timeTokens[j]);
					
					if (np.isDigitString(timeTokens[j]) && np.isDigitString(timeTokens[j + 1])){
						twoNumTgt = true;
						isModified[j] = true;
						break;
					}
				}
				
				if (twoNumTgt){
					continue;
				}
				
				wholeString.append(token);
				timeInput = wholeString.toString().trim();

				// System.err.println(dateInput);
				if (isTime(timeInput)) {
					LOGGER.info("TIMEWORDS " + n + " " + token);

					try {
						timeInput = datm.parseTimeInput(timeInput);
						// timeInput = datm.parseTime(timeInput);
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
	private void changeNDateWords(String[] dateTokens, boolean[] isModified,
			int numWordJoin) {
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		NumberParser np = NumberParser.getInstance();
		for (int i = numWordJoin - 1; i < dateTokens.length; i++) {
			String token = dateTokens[i];
			StringBuffer wholeString = new StringBuffer();
			String dateInput;

			if (isParseFree(token)) {
				isModified[i] = true;
				continue;
			}

			// combine n words:
			if (allNotModified(isModified, i, numWordJoin)) {
				boolean twoNumTgt = false;
				for (int j = i - numWordJoin + 1; j <= i - 1; j++) {
					wholeString.append(dateTokens[j]);
					if (np.isDigitString(dateTokens[j]) && np.isDigitString(dateTokens[j+1])){
						twoNumTgt = true;
						isModified[j] = true;
						break;
					}
				}
				
				if (twoNumTgt){
					continue;
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
					allNWordsModified(isModified, i, numWordJoin);
					dateTokens[i] = dateInput;
					for (int j = i - numWordJoin + 1; j <= i - 1; j++) {
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
		TimeParser tp = TimeParser.getInstance();
		try {
			tp.parseTimeInput(input);
			LOGGER.info("TIME PARSED: " + input);
		} catch (TimeErrorException | InvalidTimeException e) {
			LOGGER.info("TIME EXCEPTION: " + e.getMessage());
			return false;
		} catch (Exception e) {
			LOGGER.info("ERROR: " + input);
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
	 * not fully integrated yet
	 * 
	 * @param holidayString
	 */
	private String parseDay(String holidayString) {

		String dayString = getDayNtTmrYtd(holidayString);

		LOGGER.info("NT TMR YTD : " + dayString);

		dayString = getTmrYtd(dayString);

		LOGGER.info("TMR YTD : " + dayString);

		return dayString;
	}

	/**
	 * @param dayString
	 * @return
	 */
	private String getTmrYtd(String dayString) {
		String[] dayTokens = dayString.split(" ");
		StringBuffer dayBuilder = new StringBuffer(" ");

		SpecialWordParser swp = SpecialWordParser.getInstance();
		DateAndTimeManager datm = DateAndTimeManager.getInstance();

		String tmrTdyStr = STRING_EMPTY;

		boolean isStart = false;

		for (int i = 0; i < dayTokens.length; i++) {
			String token = dayTokens[i];
			boolean isTmrYtdVariation = swp.isTmrYtd(token);

			LOGGER.info("tmrYtd: " + token);

			if (isParseFree(token)) {
				if (isStart) {
					isStart = false;
					String passString = dayBuilder.toString().trim();
					try {
						dayTokens[i - 1] = datm.parseDayToDate(passString);
					} catch (InvalidDayException e) {
						// unreachable
						assert (false);
					}
					dayBuilder = new StringBuffer();
				}
			} else if (isTmrYtdVariation && !isStart) {
				dayBuilder.append(token + " ");
				dayTokens[i] = null;
				isStart = true;

				LOGGER.info("isTmrYtdVariation && !isStart: "
						+ dayBuilder.toString());
			} else if (isTmrYtdVariation && isStart) {
				dayTokens[i] = null;
				dayBuilder.append(token + " ");

				LOGGER.info("isTmrYtdVariation && isStart: "
						+ dayBuilder.toString());
			} else {
				if (isStart) {
					isStart = false;
					String passString = dayBuilder.toString().trim();
					try {
						dayTokens[i - 1] = datm.parseDayToDate(passString);
					} catch (InvalidDayException e) {
						// unreachable
						assert (false);
					}
					dayBuilder = new StringBuffer();
				}
			}
		}

		if (isStart) {
			String passString = dayBuilder.toString().trim();
			try {
				tmrTdyStr = datm.parseDayToDate(passString);
			} catch (InvalidDayException e) {
				// unreachable
				assert (false);
			}
		}

		dayBuilder = new StringBuffer();

		LOGGER.info("DAYTOKENS: " + dayTokens.length);

		dayBuilder = buildString(dayTokens, dayBuilder);
		dayBuilder.append(tmrTdyStr);

		dayString = dayBuilder.toString().trim();
		return dayString;
	}

	/**
	 * @param dayTokens
	 * @param dayString
	 * @param swp
	 * @param datm
	 * @param dp
	 * @param isModified
	 * @return
	 */
	private String getDayNtTmrYtd(String holidayString) {
		String[] dayTokens = holidayString.split(" ");
		StringBuffer dayBuilder = new StringBuffer();
		SpecialWordParser swp = SpecialWordParser.getInstance();
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		DayParser dp = DayParser.getInstance();
		boolean[] isModified = new boolean[dayTokens.length];
		boolean isTdyOnce = false;
		boolean isNowOnce = false;

		initializeArray(isModified);

		for (int i = 0; i < dayTokens.length; i++) {
			String firstToken = dayTokens[i];
			StringBuffer changedTokens = new StringBuffer();

			if (isParseFree(firstToken)) {
				isModified[i] = true;
				continue;
			}

			if (dp.isDay(firstToken) || swp.isWk(firstToken)) {
				isModified[i] = true;

				if (dp.isTdy(firstToken)) {
					if (isTdyOnce) {
						dayTokens[i] = null;
						continue;
					} else {
						changedTokens.append(firstToken);
						isTdyOnce = true;
					}
				} else if (dp.isNow(firstToken)) {
					if (isNowOnce) {
						dayTokens[i] = null;
						continue;
					} else {
						changedTokens.append(firstToken);
						isNowOnce = true;
					}
				} else {

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
				}

				try {
					// System.err.println(changedTokens.toString());
					dayTokens[i] = datm.parseDayToDate(changedTokens.toString()
							.trim());
				} catch (InvalidDayException e) {
					assert (false);
				}
			}
		}

		dayBuilder = buildString(dayTokens, dayBuilder);
		return dayBuilder.toString().trim();
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

		HolidayDatesParser holidayParser = HolidayDatesParser.getInstance();
		for (int i = 2; i < numberInputTokens.length; i++) {
			String token = numberInputTokens[i];

			String holidayInput;
			String pastOneToken, pastTwoToken;

			if (isParseFree(token)) {
				isModified[i] = true;
				continue;
			}

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

			if (isParseFree(token)) {
				isModified[i] = true;
				continue;
			}

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

			if (isParseFree(token)) {
				isModified[i] = true;
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
	private StringBuffer buildString(String[] anyTokens, StringBuffer anyString) {
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
	protected String parseNumber(String input) {
		NumberParser np = NumberParser.getInstance();
		Scanner sc = new Scanner(input);
		StringBuffer changedString = new StringBuffer();
		StringBuffer numberString = new StringBuffer();
		boolean isNumberContinue = false;

		while (sc.hasNext()) {
			String token = sc.next();
			if (isParseFree(token) || !isNumber(token)) {
				if (isNumberContinue) {
					String realNumber = parseOnlyNumber(numberString
							.toString().trim());
					changedString.append(realNumber + " ");
					numberString = new StringBuffer();
				}

				changedString.append(token + " ");
				isNumberContinue = false;
			} else if (np.isDigitString(token)) {
				if (isNumberContinue) {
					String realNumber = parseOnlyNumber(numberString
							.toString().trim());
					changedString.append(realNumber + " ");
					numberString = new StringBuffer();
				}

				// System.err.println("AAA: " + token);
				isNumberContinue = false;
				String realNumber = parseOnlyNumber(token, false);
				changedString.append(realNumber + " ");
				numberString = new StringBuffer();
			} else {
				if (!isNumberContinue) {
					isNumberContinue = true;
				}
				numberString.append(token + " ");
			}
		}

		String realNumber = parseOnlyNumber(numberString.toString()
				.trim(), false);
		if (realNumber != null) {
			changedString.append(realNumber + " ");
		}

		sc.close();
		return changedString.toString().trim();
	}
	
	/**
	 * parseOnlyHoliday only can parse holiday...
	 * @param input
	 * @return
	 */
	protected String parseOnlyHoliday(String input){
		HolidayDatesParser hdp = HolidayDatesParser.getInstance();
		return hdp.replaceHolidayDate(input);
	}
	
	/**
	 * parseNumber: parse a language number to a real number String, ex: one to
	 * 1. It returns null when error occurs.
	 * 
	 * @param numberString
	 *            : language number or normal number
	 * @return String
	 */
	private String parseOnlyNumber(String numberString) {
		return parseOnlyNumber(numberString, true);
	}
	
	/**
	 * can only parse one to 1
	 * @param numberString
	 * @param isStrict
	 * @return
	 */
	private String parseOnlyNumber(String numberString, boolean isStrict) {
		NumberParser parser = NumberParser.getInstance();
		return parser.parseTheNumbers(numberString, isStrict);
	}

	protected boolean isNumber(String numberString) {
		NumberParser parser = NumberParser.getInstance();
		return parser.parseTheNumbers(numberString, true) != null;
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

	/**
	 * @deprecated : moved to DayParser
	 * @param dayString
	 * @return
	 */
	@SuppressWarnings("unused")
	private String parseTodayAndNow(String dayString) {
		String[] todayAndNowTokens = dayString.split(" ");
		StringBuffer todayAndNowBuilder = new StringBuffer();
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		String todayDate = datm.getTodayDate();
		String now = datm.getTodayDateAndTime();

		for (int i = 0; i < todayAndNowTokens.length; i++) {
			String token = todayAndNowTokens[i];
			if (isToday(token)) {
				todayAndNowTokens[i] = todayDate;
			} else if (isNow(token)) {
				todayAndNowTokens[i] = now;
			}
		}

		todayAndNowBuilder = buildString(todayAndNowTokens, todayAndNowBuilder);

		return todayAndNowBuilder.toString().trim();
	}
	
	public static void main(String[] args) {
		DateAndTimeRetriever datr = DateAndTimeRetriever.getInstance();
		/*
		 * System.out .println(datr.getAlphaNumericSpaceDesc(
		 * "I am looking for Lynnette. She is going home on Monday."));
		 * System.out .println(datr.getAlphaNumericSpaceDesc(
		 * "I am looking for Lynnette. She is going home on 1/4/15 12:00."));
		 * System.out.println(datr.parseNumber("one one one aaa one one one"));
		 * System.out.println(datr.parseNumber("one one one aaa"));
		 * System.out.println(datr.parseNumber("aaa")); System.out
		 * .println(datr.
		 * parseHolidayDates("last Christmas I gave you my heart Christmas"));
		 * System.out .println(datr.parseHolidayDates(
		 * "last New Year I gave you my heart Christmas")); System.out
		 * .println(datr
		 * .parseHolidayDates("last April Fool Day I gave you my heart Christmas"
		 * )); System.out.println(datr.parseDay("Monday I want to eat Monday"));
		 * System.out .println(datr.parseDay(
		 * "next nxt NXT prev Monday I want to catch Pokemon!")); System.out
		 * .println
		 * (datr.parseDay("next ASH nxt Monday I want to catch Pokemon nxt Fri !"
		 * )); System.out
		 * .println(datr.parseDate("1 / 11 / 2014 , I watch movie in 1 December"
		 * )); System.out.println(datr.parseTime("1 am"));
		 * System.out.println(datr.parseDate("11/11/2015"));
		 * System.out.println(datr.parseTime("11:00"));
		 * System.out.println(datr.formatDateAndTimeInString("aaa"));
		 */
		/*
		 * try { System.out.println(datr.formatDateAndTimeInString(
		 * "aaa at 11/3 by 3/4 11pm")); } catch (InvalidQuotesException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 

		try {
			String noQuoteDesc = datr.removeParseFreeZone("\" aaaa \" bbbb ");
			System.out.println(noQuoteDesc);
		} catch (InvalidQuotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * try { System.out .println(datr.formatDateAndTimeInString(
		 * "do cs2010 assignment by nxt nxt Wk")); } catch
		 * (InvalidQuotesException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 

		try {
			System.out.println(datr.formatDateAndTimeInString("11:00"));
		} catch (InvalidQuotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// VERY IMP TEST CASE
		try {
			System.out.println(datr.searchTimeAndDate("12:00 06/04/2014")
					.toString());
		} catch (InvalidQuotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(datr.parseTimeWord("1 hour"));

		System.out.println(datr.parseTodayAndNow("find Lynnette by Today"));

		try {
			System.out
					.println(datr
							.formatDateAndTimeInString("\"FRIDAY\" asked me to deliver potion to Ivy nxt nxt Tue"));
		} catch (InvalidQuotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			System.out.println(datr
					.formatDateAndTimeInString("one one one 1 one one "));
		} catch (InvalidQuotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// VERY IMP TEST CASE
		try {
			System.out.println(datr.searchTimeAndDate(
					"20:14 05/04/2014 03/02/2014").toString());
		} catch (InvalidQuotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			System.out.println(datr
					.formatDateAndTimeInString("aaa tmr 11.59pm"));
		} catch (InvalidQuotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*BUG HAPPENS when two numbers come together*/
		
		/*
		try {
			System.out.println(datr
					.formatDateAndTimeInString("use calculator 570 11/11/15"));
		} catch (InvalidQuotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println(datr
					.formatDateAndTimeInString("use calculator 570 11:11"));
		} catch (InvalidQuotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*
		*/
		
		System.out.println(datr.parseNumber("one one one 1 aaa one one one"));
		System.out.println(datr.parseNumber("one one one aaa"));

		// System.out.println("AAA".split(" ").length);
	}

}
