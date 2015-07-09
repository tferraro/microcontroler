package tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import businessModel.MicroController;
import businessModel.Program;
import UI.ProgramParser;

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
		assertEquals("0\n", baos.toString());
	}

}
