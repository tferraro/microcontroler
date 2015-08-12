package tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import ui.command.ProgramParser;
import exceptions.ProgramParserException;
import businessModel.MicroController;
import businessModel.Program;

public class TestsOverProgramParser {

	@Test
	public void tryParsingStringProgram() {
		Program prog1 = new ProgramParser()
				.parse("LODV 15\nSWAP\nLODV 5\nADD\n");
		MicroController micro = new MicroController();
		micro.load(prog1);
		micro.start();
		micro.execute();
	}

	@Test
	public void tryParsingStringProgramUsingAllInstructions() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream newPrintStream = new PrintStream(baos);
		PrintStream stdout = System.out;
		System.setOut(newPrintStream);
		Program prog1 = new ProgramParser()
				.parse("LODV 15\nLOD 0\nSWAP\nLODV 5\nSWAP\nDIV\nSTR 0\nLODV 0\nSUB\nNOP\nADD\nPRNT\nIFNZ 1\nwhnz 1\n");
		MicroController micro = new MicroController();
		micro.load(prog1);
		micro.start();
		micro.execute();
		System.out.flush();
		System.setOut(stdout);
		assertEquals("0" + System.getProperty("line.separator"), baos.toString());
	}

	@Test
	public void tryParsingWithSintaxErrorAndFail() {
		try {
			new ProgramParser().parse("LODV15\n");
			fail("No Parser Exception Throwed");
		} catch (ProgramParserException e) {
			assertEquals("Wrong sintax on Instruction: LODV15", e.getMessage());
		}
	}

	@Test
	public void tryParsingWithSemanticErrorInvalidArgumentTypeAndFail() {
		try {
			new ProgramParser().parse("LODV Q\n");
			fail("No Parser Exception Throwed");
		} catch (ProgramParserException e) {
			assertEquals("Semantic Error: For input string: \"Q\"",
					e.getMessage());
		}
	}

	@Test
	public void tryParsingWithSemanticErrorNoArgumentsAndFail() {
		try {
			new ProgramParser().parse("LODV\n");
			fail("No Parser Exception Throwed");
		} catch (ProgramParserException e) {
			assertEquals("Semantic Error: wrong number of arguments",
					e.getMessage());
		}
	}

	@Test
	public void tryParsingWithSemanticErrorToManyArgumentsAndFail() {
		try {
			new ProgramParser().parse("LODV 12 1\n");
			fail("No Parser Exception Throwed");
		} catch (ProgramParserException e) {
			assertEquals("Semantic Error: wrong number of arguments",
					e.getMessage());
		}
	}

}
