package devinfalgoust.util.timerlogger;

import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The TimerLogger class is designed to make logging fast and easy.
 * 
 * @author Devin Falgoust
 * 
 * Copyright 2013 Devin Falgoust
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
public class TimerLogger {

	/******************************
	 * ----------------------------
	 * Logger Use Examples
	 * ----------------------------
	 * logger.start("TimerName");
	 * logger.end("TimerName");
	 * logger.log("Message");
	 * logger.logError("Message");
	 * ----------------------------
	 ******************************/

	// Only tabify when tabifyFlag is set
	private Boolean tabifyFlag;

	// Printstream used for normal output
	private PrintStream out;

	// Printstream used for error output
	private PrintStream err;

	// Map to hold the timers and give them names
	private Map<String, Timer> timers;

	// Number of tabs to place in tabify
	private Integer numTabs;

	/******************************
	 * Logging Functions
	 ******************************/

	/**
	 * Start: Starts a timer with the given name attached and
	 * logs that it was started
	 * -
	 * This function returns a Logger so that commands can be stacked
	 * 
	 * @param timerName
	 *            - Name of Timer
	 * @return Logger - useful for stacking calls
	 */
	public TimerLogger start(String timerName) {
		timers.put(timerName, new Timer());
		timers.get(timerName).start();
		out.println(tabify() + timerName + " - Started ... ");
		numTabs++;
		return this;
	}

	/**
	 * Start: Starts a timer with the given name attached and
	 * logs that it was started. The timer's name will be taken using the
	 * Object passed in's toString() function.
	 * -
	 * This function returns a Logger so that commands can be stacked
	 * 
	 * @param getTimerNameFrom
	 *            - Object Used to Get Name of Timer
	 * @return Logger - useful for stacking calls
	 */
	public TimerLogger start(Object getTimerNameFrom) {
		return start(getTimerNameFrom.toString());
	}

	/**
	 * End: Ends a timer with the given name attached and logs
	 * that it is done. If there is no timer with the given name,
	 * it will log an error.
	 * -
	 * This function returns a Logger so that commands can be stacked
	 * 
	 * @param timerName
	 *            - Name of Timer
	 * @return Logger - useful for stacking calls
	 */
	public TimerLogger end(String timerName) {
		try {
			Long time = timers.get(timerName).end();
			timers.remove(timerName);
			numTabs--;
			out.println(tabify() + timerName + String.format(" - Done - %d (ms)", time));
		} catch (NullPointerException e) {
			logError("Sorry, \"" + timerName + "\" was never started.");
			// Sleep for 1ms. This ensure that the error is printed in order
			// and not later on down the road
			try {
				Thread.sleep(1);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
		return this;
	}

	/**
	 * End: Ends a timer with the given name attached and logs
	 * that it is done. The timer's name will be taken using the
	 * Object passed in's toString() function.
	 * -
	 * This function returns a Logger so that commands can be stacked
	 * 
	 * @param getTimerNameFrom
	 *            - Object Used to Get Name of Timer
	 * @return Logger - useful for stacking calls
	 */
	public TimerLogger end(Object getTimerNameFrom) {
		return end(getTimerNameFrom.toString());
	}

	/**
	 * Logs a given message on the out stream
	 * -
	 * This function returns a Logger so that commands can be stacked
	 * 
	 * @param logMessage
	 *            - Message to Log
	 * @return Logger - useful for stacking calls
	 */
	public TimerLogger log(String logMessage) {
		out.println(tabify() + "-- " + logMessage);
		return this;
	}

	/**
	 * Logs a given error message on the err stream
	 * -
	 * This function returns a Logger so that commands can be stacked
	 * 
	 * @param errorMessage
	 *            - Error Message to Log
	 * @return Logger - useful for stacking calls
	 */
	public TimerLogger logError(String errorMessage) {
		err.println(tabify() + "-- " + errorMessage);
		return this;
	}

	/******************************
	 * Constructors
	 ******************************/

	/**
	 * Default Constructor: Init with System.out, System.err, and
	 * isTabify as true
	 */
	public TimerLogger() {
		tabifyFlag = true;
		out = System.out;
		err = System.err;
		timers = new HashMap<String, Timer>();
		numTabs = 0;
	}

	/**
	 * Constructor: Init with a given PrintStream, setting both the
	 * out stream and err stream to it, and setting isTabify to true
	 * 
	 * @param outAndErrStream
	 *            - PrintStream used for both output and errors
	 */
	public TimerLogger(PrintStream outAndErrStream) {
		tabifyFlag = true;
		out = err = outAndErrStream;
		timers = new HashMap<String, Timer>();
		numTabs = 0;
	}

	/**
	 * Constructor: Init with two PrintStreams, the first out and
	 * the second err, setting isTabify to true
	 * 
	 * @param outStream
	 *            - PrintStream used for output
	 * @param errStream
	 *            - PrinStream used for errors
	 */
	public TimerLogger(PrintStream outStream, PrintStream errStream) {
		tabifyFlag = true;
		out = outStream;
		err = errStream;
		timers = new HashMap<String, Timer>();
		numTabs = 0;
	}

	/**
	 * Constructor: Init with a given tabify, System.out, & System.err
	 * 
	 * @param isTabify
	 *            - Boolean for whether to tabify or not
	 */
	public TimerLogger(Boolean isTabify) {
		tabifyFlag = isTabify;
		out = System.out;
		err = System.err;
		timers = new HashMap<String, Timer>();
		numTabs = 0;
	}

	/**
	 * Constructor: Init with a given tabify and a given PrintStream,
	 * setting both the out stream and err stream to it
	 * 
	 * @param outAndErrStream
	 *            - PrintStream used for both output and errors
	 * @param isTabify
	 *            - Boolean for whether to tabify or not
	 */
	public TimerLogger(PrintStream outAndErrStream, Boolean isTabify) {
		tabifyFlag = isTabify;
		isTabify = true;
		out = err = outAndErrStream;
		timers = new HashMap<String, Timer>();
		numTabs = 0;
	}

	/**
	 * Constructor: Init with a given tabify and two PrintStreams,
	 * the first out and the second err
	 * 
	 * @param outStream
	 *            - PrintStream used for output
	 * @param errStream
	 *            - PrinStream used for errors
	 * @param isTabify
	 *            - Boolean for whether to tabify or not
	 */
	public TimerLogger(PrintStream outStream, PrintStream errStream, Boolean isTabify) {
		tabifyFlag = isTabify;
		isTabify = true;
		out = outStream;
		err = errStream;
		timers = new HashMap<String, Timer>();
		numTabs = 0;
	}

	/**
	 * Basic Copy Constructor
	 * 
	 * @param other
	 *            - Other Logger to Copy
	 */
	public TimerLogger(TimerLogger other) {
		tabifyFlag = other.tabifyFlag;
		out = other.out;
		err = other.err;
		timers = other.timers;
		numTabs = other.numTabs;
	}

	/******************************
	 * Getters and Setters
	 ******************************/

	/**
	 * Get the value of isTabify
	 * 
	 * @return Boolean
	 */
	public Boolean isTabify() {
		return tabifyFlag;
	}

	/**
	 * Set isTabify to true or false
	 * 
	 * @param isTabify
	 */
	public void setTabifyFlag(Boolean isTabify) {
		tabifyFlag = isTabify;
	}

	/**
	 * Set the PrintStream used for standard output
	 * 
	 * @param out
	 */
	public void setOutPrintStream(PrintStream out) {
		this.out = out;
	}

	/**
	 * Set the PrintStream used for standard error output
	 * 
	 * @param err
	 */
	public void setErrPrintStream(PrintStream err) {
		this.err = err;
	}

	/**
	 * Set the PrintStreams used for standard output and standard error output
	 * 
	 * @param stream
	 */
	public void setOutAndErrPrintStreams(PrintStream stream) {
		out = stream;
		err = stream;
	}

	/******************************
	 * Helpers
	 ******************************/

	/**
	 * Returns a string of tabs, based upon logger hierarchy
	 * 
	 * @return String - Tabs Used to Show Hierarchy
	 */
	private String tabify() {
		String tabs = "";
		// Only adds tabs if tabifyFlag is set
		for (int i = 0; tabifyFlag && i < numTabs; i++) {
			tabs += "   ";
		}
		return tabs;
	}

	/**
	 * Timer class that controls start and stop functionality,
	 * returning a Long with time difference on stop
	 */
	private class Timer {
		private Date startDate;
		private Date endDate;

		private void start() {
			startDate = new Date();
		}

		private Long end() {
			endDate = new Date();
			Long time = endDate.getTime() - startDate.getTime();
			return time;
		}
	}

}
