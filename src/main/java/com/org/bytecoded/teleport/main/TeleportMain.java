package com.org.bytecoded.teleport.main;

import java.io.InputStream;
import java.util.Scanner;

public class TeleportMain {

	private static final String FILE_NAME = "teleportInput.txt";

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(FILE_NAME);
		if (null != is) {

			Scanner scanner = new Scanner(is);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				sb.append(line).append("\n");
			}

			scanner.close();

		}
		System.out.println(sb.toString());
	}

}
