package tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import exceptions.IllegalValueException;
import businessModel.MicroController;
import businessModel.Program;
import businessModel.ProgramBuilder;
import businessModel.instructions.Ifnz;

public class TestsOverEatisInstructions {

	private MicroController micro;
	private Program prog1;

	@Before
	public void setUp() {
		micro = new MicroController();
		prog1 = new Program();
	}

	@Test
	public void executeIfNotZeroFunction() {
		prog1.addInstruction(new Ifnz(5));
		micro.load(prog1);
		micro.start();
		micro.execute();
	}

	@Test
	public void stepIfNotZeroFunctionCheckingRegisters() {
		micro.load(new ProgramBuilder().ifnz(1).lodv(15).lodv(1).ifnz(2)
				.lodv(0).ifnz(1).lodv(15).build());
		// IFNZ 1
		// /LODV 15
		// LODV 1
		// IFNZ 2
		// LODV 0
		// IFNZ 1
		// /LODV 15
		micro.start();
		micro.step();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		micro.step();
		assertEquals(1, micro.getRegister("A").getValue(), 0);
		micro.step();
		micro.step();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		micro.execute();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
	}

	@Test
	public void stepBackIfNotZeroFunctionCheckingRegisters() {
		// Debug Comments
		// Register regA = micro.getRegister("A");
		// Register regb = micro.getRegister("B");
		// Register regip = micro.getRegister("IP");

		micro.load(new ProgramBuilder().ifnz(1).lodv(15).lodv(1).ifnz(2)
				.lodv(0).ifnz(1).lodv(15).build());
		// IFNZ 1
		// /LODV 15
		// LODV 1
		// IFNZ 2
		// LODV 0
		// IFNZ 1
		// /LODV 15
		micro.start();
		micro.step();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		micro.step();
		assertEquals(1, micro.getRegister("A").getValue(), 0);
		micro.stepBack();
		micro.stepBack();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		micro.step();
		micro.step();
		assertEquals(1, micro.getRegister("A").getValue(), 0);
	}

	@Test
	public void stepWhileNotZeroCheckingRegisters() {
		// Debug Comments
		// Register regA = micro.getRegister("A");
		// Register regb = micro.getRegister("B");
		// Register regip = micro.getRegister("IP");

		micro.load(new ProgramBuilder().lodv(10).swap().lodv(1).sub().swap()
				.whnz(4).build());
		// LODV 10
		// /SWAP
		// /LODV 1
		// /SUB
		// /SWAP
		// WHNZ 4
		micro.start();
		for (int i = 0; i < 51; i++)
			micro.step();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(0, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void stepAndBackWhileNotZeroCheckingRegisters() {
		// Debug Comments
		// Register regA = micro.getRegister("A");
		// Register regb = micro.getRegister("B");
		// Register regip = micro.getRegister("IP");

		micro.load(new ProgramBuilder().lodv(10).swap().lodv(1).sub().swap()
				.whnz(4).build());
		// LODV 10
		// /SWAP
		// /LODV 1
		// /SUB
		// /SWAP
		// WHNZ 4
		micro.start();
		for (int i = 0; i < 50; i++)
			micro.step();
		for (int i = 0; i < 50; i++)
			micro.stepBack();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(0, micro.getRegister("B").getValue(), 0);
		assertEquals(0, micro.getRegister("IP").getValue(), 0);
	}

	@Test
	public void loadIfnzWithNotSupportedValueAndFail() {
		try {
			micro.load(new ProgramBuilder().ifnz(0).build());
			fail("No Exception Throwed");
		} catch (IllegalValueException e) {
			assertEquals("Negative and Zero Values not supported.",
					e.getMessage());
		}
	}

	@Test
	public void loadWhnzWithNotSupportedValueAndFail() {
		try {
			micro.load(new ProgramBuilder().whnz(0).build());
			fail("No Exception Throwed");
		} catch (IllegalValueException e) {
			assertEquals("Negative and Zero Values not supported.",
					e.getMessage());
		}
	}

	@Test
	public void haltExecuteCheckingRegisters() {
		micro.load(new ProgramBuilder().halt().lodv(5).build());
		micro.start();
		micro.execute();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		micro.execute();
		assertEquals(5, micro.getRegister("A").getValue(), 0);
	}

	@Test
	public void haltStepCheckingRegisters() {
		micro.load(new ProgramBuilder().halt().lodv(5).build());
		micro.start();
		micro.step();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		micro.step();
		assertEquals(5, micro.getRegister("A").getValue(), 0);
	}

	@Test
	public void haltStepBackCheckingRegisters() {
		micro.load(new ProgramBuilder().halt().lodv(5).build());
		micro.start();
		micro.step();
		micro.stepBack();
		micro.execute();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		micro.execute();
		assertEquals(5, micro.getRegister("A").getValue(), 0);
	}

	@Test
	public void prntCheckingOutRedirectingToOwnPrintStream() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream newPrintStream = new PrintStream(baos);
		PrintStream stdout = System.out;
		System.setOut(newPrintStream);
		micro.load(new ProgramBuilder().lodv(15).prnt().build());
		micro.start();
		micro.execute();
		System.out.flush();
		System.setOut(stdout);
		assertEquals("15\n", baos.toString());
	}

	@Test
	public void prntWhnzCheckingOutRedirectingToOwnPrintStream() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream newPrintStream = new PrintStream(baos);
		PrintStream stdout = System.out;
		System.setOut(newPrintStream);
		micro.load(new ProgramBuilder().lodv(10).swap().lodv(1).sub().swap()
				.prnt().whnz(5).build());
		micro.start();
		micro.execute();
		System.out.flush();
		System.setOut(stdout);
		assertEquals("9\n8\n7\n6\n5\n4\n3\n2\n1\n0\n", baos.toString());
	}

}
