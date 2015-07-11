package tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;

import org.junit.Before;
import org.junit.Test;

import businessModel.MicroController;
import ui.command.RunnerCommand;

public class TestsOverCommandRunner {

	private ByteArrayOutputStream baos = new ByteArrayOutputStream();
	private PrintStream newPrintStream = new PrintStream(baos);
	private MicroController micro = RunnerCommand.getMicro();
	private File file;

	@Before
	public void setUp() {
		baos.reset();
		newPrintStream.flush();
		micro.clearMicro();
		try {
			file = File.createTempFile("prueba", "batis");
			file.deleteOnExit();
			Writer writer = new PrintWriter(file);
			writer.write("LODV 15\nSWAP\nLODV 5\nADD\n");
			writer.close();
		} catch (IOException e) {
			fail("Couldn't Create Test File");
		}

	}

	@Test
	public void executeRunnerWithTempFile() {
		// Override stdout to avoid spam
		PrintStream stdout = System.out;
		System.setOut(newPrintStream);

		RunnerCommand.main(new String[] { file.getAbsoluteFile().toString() });
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(20, micro.getRegister("B").getValue(), 0);

		System.out.flush();
		System.setOut(stdout);
	}

	@Test
	public void tryCheckArgumentsMethodOfRunnerWithOneFlag() {
		RunnerCommand.checkArguments(new String[] { "-d",
				file.getAbsoluteFile().toString() });
	}

	@Test
	public void tryCheckArgumentsMethodOfRunnerWithBadPathAndFail() {
		try {
			RunnerCommand.checkArguments(new String[] { "-d" });
			fail("No Exception Throwed");
		} catch (Exception e) {
			assertEquals("File does not exist.", e.getMessage());
		}
	}

	@Test
	public void tryCheckArgumentsMethodOfRunnerWithNoArgsAndFail() {
		try {
			RunnerCommand.checkArguments(new String[] {});
			fail("No Exception Throwed");
		} catch (Exception e) {
			assertEquals("Feed me with arguments!", e.getMessage());
		}
	}

	@Test
	public void tryRunnerWithTwoArguments() {
		// Override stdout to avoid spam
		PrintStream stdout = System.out;
		System.setOut(newPrintStream);

		RunnerCommand.main(new String[] { "-d",
				file.getAbsoluteFile().toString() });
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(20, micro.getRegister("B").getValue(), 0);

		System.out.flush();
		System.setOut(stdout);
	}
}
