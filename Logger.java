package log;

import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The Logger class is designed to make logging fast and easy.
 * 
 * @author Devin Falgoust
 */
public class Logger {

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

	private Boolean isTabify;
	private PrintStream out;
	private PrintStream err;
	private Map<String, Timer> timers;
	private Integer numTabs;

	/******************************
	 * Logging Functions
	 ******************************/

	/**
	 * Start: Starts a timer with the given name attached and
	 * logs that it was started
	 * 
	 * @param timerName
	 *            - Name of Timer
	 */
	public void start(String timerName) {
		timers.put(timerName, new Timer());
		timers.get(timerName).start();
		out.println((isTabify ? tabify() : "") + timerName + " - Started ... ");
		numTabs++;
	}

	/**
	 * End: Ends a timer with the given name attached and logs
	 * that it is done. If there is no timer with the given name,
	 * it will log an error.
	 * 
	 * @param timerName
	 *            - Name of Timer
	 */
	public void end(String timerName) {
		try {
			Long time = timers.get(timerName).end();
			timers.remove(timerName);
			numTabs--;
			out.println((isTabify ? tabify() : "") + timerName + String.format(" - Done - %d (ms)", time));
		} catch (NullPointerException e) {
			logError("Sorry, \"" + timerName + "\" was not started.");
		}
	}

	/**
	 * Logs a given message on the out stream
	 * 
	 * @param logMessage
	 *            - Message to Log
	 */
	public void log(String logMessage) {
		out.println("-- " + logMessage);
	}

	/**
	 * Logs a given error message on the err stream
	 * 
	 * @param errorMessage
	 *            - Error Message to Log
	 */
	public void logError(String errorMessage) {
		err.println("-- " + errorMessage);
	}

	/******************************
	 * Constructors
	 ******************************/

	/**
	 * Default Constructor: Init with System.out, System.err, and
	 * isTabify as true
	 */
	public Logger() {
		isTabify = true;
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
	public Logger(PrintStream outAndErrStream) {
		isTabify = true;
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
	public Logger(PrintStream outStream, PrintStream errStream) {
		isTabify = true;
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
	public Logger(Boolean isTabify) {
		this.isTabify = isTabify;
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
	public Logger(PrintStream outAndErrStream, Boolean isTabify) {
		this.isTabify = isTabify;
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
	public Logger(PrintStream outStream, PrintStream errStream, Boolean isTabify) {
		this.isTabify = isTabify;
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
	public Logger(Logger other) {
		isTabify = other.isTabify;
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
		return isTabify;
	}

	/**
	 * Set isTabify to true or false
	 * 
	 * @param isTabify
	 */
	public void setIsTabify(Boolean isTabify) {
		this.isTabify = isTabify;
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
		for (int i = 0; i < numTabs; i++) {
			tabs += "   ";
		}
		return tabs;
	}

	/**
	 * Timer class that controls start and stop functionality,
	 * returning a Long on stop
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
