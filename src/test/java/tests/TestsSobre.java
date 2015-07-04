package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import businessModel.MicroController;
import businessModel.Register;
import businessModel.instructions.Add;
import businessModel.instructions.Nop;

public class TestsSobre {

	private MicroController micro;

	@Before
	public void setUp() {
		micro = new MicroController();
	}

	@Test
	public void executeNopInstruction() {
		micro.addInstruction(new Nop());
		micro.executeProgram();
	}

	@Test
	public void executeAddInstruction() {
		micro.addInstruction(new Add());
		micro.executeProgram();
	}

	@Test
	public void executeAddInstructionCheckingRegisters() {
		micro.setRegister("A", 100);
		micro.setRegister("B", 50);
		micro.addInstruction(new Add());
		micro.executeProgram();
		Register regA = micro.getRegister("A");
		Register regB = micro.getRegister("B");
		assertEquals(0, regA.getValue(), 0);
		assertEquals(150, regB.getValue(), 0);
	}

	@Test
	public void executeAddInstructionWithOverflowCheckingRegisters() {
		micro.setRegister("A", 150);
		micro.setRegister("B", 200);
		micro.addInstruction(new Add());
		micro.executeProgram();
		Register regA = micro.getRegister("A");
		Register regB = micro.getRegister("B");
		assertEquals(95, regA.getValue(), 0);
		assertEquals(255, regB.getValue(), 0);
	}

}
