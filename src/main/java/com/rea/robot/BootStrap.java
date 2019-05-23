package com.rea.robot;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.rea.robot.exception.ToyRobotException;
import com.rea.robot.simulator.SquareBoard;
import com.rea.robot.simulator.ToyRobot;

public class BootStrap {

	final static Logger logger = Logger.getLogger(BootStrap.class.getName());

	// TODO can change the file location
	private static String fileName = System.getProperty("user.home") + File.separator
			+ "rea-robot.txt";

	public static void main(String[] args) {

		FileHandler fh;

		try {
			// TODO can change the log location
			fh = new FileHandler(System.getProperty("user.home") + File.separator + "rea.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

		} catch (SecurityException e) {
			logger.warning(e.getMessage());
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}

		Console console = System.console();
		logger.warning("console" + console);

		if (console == null) {
			logger.warning("No console.");
			System.exit(1);
		}

		SquareBoard squareBoard = new SquareBoard(4, 4);
		ToyRobot robot = new ToyRobot();
		Game game = new Game(squareBoard, robot);

		logger.info("Toy Robot Simulator");
		logger.info("Enter a command, Valid commands are:");
		logger.info("\'PLACE X,Y,F\', MOVE, LEFT, RIGHT, REPORT or EXIT or FILE");

		boolean keepRunning = true;
		while (keepRunning) {
			String inputString = console.readLine(": ");
			logger.warning("inputString : " + inputString);
			if ("FILE".equalsIgnoreCase(inputString)) {
				String outputVal = null;
				try {
					File file;
					BufferedReader br = null;
					try {
						file = new File(fileName);
						if (!file.exists()) {
							logger.warning(
									" ******* Please create the file in home dir [" + System.getProperty("user.home")
											+ "] with name rea-robot.txt and valid content in it ******");
							continue;
						}
						br = new BufferedReader(new FileReader(file));
						String st;
						while ((st = br.readLine()) != null) {
							logger.info(st);
							outputVal = game.eval(st);
							logger.info(outputVal);
						}
					} catch (IOException e) {
						logger.warning(e.getMessage());
						keepRunning = false;
					} finally {
						try {
							if (br != null)
								br.close();
						} catch (IOException e) {
							logger.warning(e.getMessage());
							keepRunning = false;
						}
					}

				} catch (ToyRobotException e) {
					logger.info(e.getMessage());
					keepRunning = false;
				}
			} else if ("EXIT".equals(inputString)) {
				keepRunning = false;
			} else {
				try {
					String outputVal = game.eval(inputString);
					logger.info(outputVal);
				} catch (ToyRobotException e) {
					logger.info(e.getMessage());
				}
			}
		}
	}

}