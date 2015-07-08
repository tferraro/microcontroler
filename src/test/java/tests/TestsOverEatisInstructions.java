package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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

}
