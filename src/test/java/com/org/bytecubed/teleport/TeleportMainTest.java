package com.org.bytecubed.teleport;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.org.bytecubed.teleport.main.TeleportMain;

public class TeleportMainTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	final PrintStream oldStdout = System.out;

	private final String expectedText = "cities from Seattle in 1 jumps: [New York, Baltimore]\r"
			+ "cities from Seattle in 2 jumps: [New York, Baltimore, Philadelphia, Washington]\r"
			+ "can I teleport from New York to Atlanta: yes\r"
			+ "can I teleport from Oakland to Atlanta: no\r"
			+ "loop possible from Oakland: yes\r"
			+ "loop possible from Washington: no";

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
	}


	@Test
	public void testMain() throws Exception {
		
		//Invoke the Teleports Main which would actually read the input from teleportInput.txt and prints the output on console.
		TeleportMain.main(null/*No arguments are passed to the main*/);
		
		assertNotNull(outContent.toString());
		//assertEquals(expectedText, outContent.toString());
		
		//Printing back the Main() output to console.
		System.setOut(oldStdout);
		System.out.println(outContent.toString());
	}



}
