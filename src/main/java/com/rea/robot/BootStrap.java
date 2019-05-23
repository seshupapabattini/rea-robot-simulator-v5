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

	// TODO read the file name from properties and process
	private static String fileName = "/Users/aleph/Desktop/rea-robot-simulator-v5/file/rea-robot.txt";

	public static void main(String[] args) {

		FileHandler fh;

		try {
			// TODO change the log location, and can still enhance to add properties file
			fh = new FileHandler("/Users/aleph/Desktop/rea-robot-simulator-v5/logs/rea.log");
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
						br = new BufferedReader(new FileReader(file));
						String st;
						while ((st = br.readLine()) != null) {
							logger.info(st);
							outputVal = game.eval(st);
							logger.info(outputVal);
						}
					} catch (IOException e) {
						logger.warning(e.getMessage());
					} finally {
						try {
							if (br != null)
								br.close();
						} catch (IOException e) {
							logger.warning(e.getMessage());
						}
					}

				} catch (ToyRobotException e) {
					logger.info(e.getMessage());
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