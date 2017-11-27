package com.org.bytecubed.teleport.main;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @description This class contains the main invocation point which reads the input text file, 
 * parses the data and invokes the corresponding Teleport APIs.
 * @author Deepti
 *
 */
public class TeleportMain {

	private static final String FILE_NAME = "teleportInput.txt";

	public static void main(String[] args) throws Exception {

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(FILE_NAME);

		if (null != is) {

			Scanner scanner = new Scanner(is);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				TeleportParser.parseLine(line); //Parse each line
			}
			scanner.close();

		}
	}

}
