package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import exceptions.IllegalValueException;
import businessModel.MicroController;
import businessModel.instructions.Add;
import businessModel.instructions.Div;
import businessModel.instructions.Lod;
import businessModel.instructions.Lodv;
import businessModel.instructions.Nop;
import businessModel.instructions.Str;
import businessModel.instructions.Sub;
import businessModel.instructions.Swap;

public class TestsOverUnitaryInstructions {

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
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(150, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeAddInstructionWithOverflowResultCheckingRegisters() {
		micro.setRegister("A", 150);
		micro.setRegister("B", 200);
		micro.addInstruction(new Add());
		micro.executeProgram();
		assertEquals(95, micro.getRegister("A").getValue(), 0);
		assertEquals(255, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeSubInstruction() {
		micro.setRegister("A", 0);
		micro.setRegister("B", 50);
		micro.addInstruction(new Sub());
		micro.executeProgram();
	}

	@Test
	public void executeSubInstructionCheckingRegisters() {
		micro.setRegister("A", 50);
		micro.setRegister("B", 100);
		micro.addInstruction(new Sub());
		micro.executeProgram();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(50, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeSubInstructionWithNegativeResultAndFail() {
		micro.setRegister("A", 100);
		micro.setRegister("B", 0);
		micro.addInstruction(new Sub());
		try {
			micro.executeProgram();
			fail();
		} catch (IllegalValueException e) {
			assertEquals("Negative Numbers are not Supported", e.getMessage());
		}
	}

	@Test
	public void executeDivInstruction() {
		micro.setRegister("A", 50);
		micro.setRegister("B", 100);
		micro.addInstruction(new Div());
		micro.executeProgram();
	}

	@Test
	public void executeDivInstructionCheckingRegisters() {
		micro.setRegister("A", 50);
		micro.setRegister("B", 100);
		micro.addInstruction(new Div());
		micro.executeProgram();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(2, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeDivInstructionCheckingTruncate() {
		micro.setRegister("A", 50);
		micro.setRegister("B", 99);
		micro.addInstruction(new Div());
		micro.executeProgram();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(1, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeDivInstructionZeroDivision() {
		micro.setRegister("A", 0);
		micro.setRegister("B", 99);
		micro.addInstruction(new Div());
		try {
			micro.executeProgram();
			fail();
		} catch (IllegalValueException e) {
			assertEquals("Zero Division", e.getMessage());
		}
	}

	@Test
	public void executeSwapInstruction() {
		micro.setRegister("A", 50);
		micro.setRegister("B", 100);
		micro.addInstruction(new Swap());
		micro.executeProgram();
	}

	@Test
	public void executeSwapInstructionCheckingRegisters() {
		micro.setRegister("A", 50);
		micro.setRegister("B", 100);
		micro.addInstruction(new Swap());
		micro.executeProgram();
		assertEquals(100, micro.getRegister("A").getValue(), 0);
		assertEquals(50, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeLodInstruction() {
		micro.addInstruction(new Lod(15));
		micro.executeProgram();
	}

	@Test
	public void executeLodInstructionCheckingRegister() {
		micro.writeOnMemoryPosition(15, 1);
		micro.addInstruction(new Lod(15));
		micro.executeProgram();
		assertEquals(1, micro.getRegister("A").getValue(), 0);
	}

	@Test
	public void executeLodInstructionWithNegativeAddressAndFail() {
		micro.addInstruction(new Lod(-1));
		try {
			micro.executeProgram();
			fail();
		} catch (IllegalValueException e) {
			assertEquals("Invalid Memory Address", e.getMessage());
		}
	}

	@Test
	public void executeLodInstructionWithOutOfBoundsAddressAndFail() {
		micro.addInstruction(new Lod(1024));
		try {
			micro.executeProgram();
			fail();
		} catch (IllegalValueException e) {
			assertEquals("Invalid Memory Address", e.getMessage());
		}
	}

	@Test
	public void executeStrInstruction() {
		micro.setRegister("A", 95);
		micro.addInstruction(new Str(15));
		micro.executeProgram();
	}

	@Test
	public void executeStrInstructionCheckingMemory() {
		micro.setRegister("A", 95);
		micro.addInstruction(new Str(15));
		micro.executeProgram();
		assertEquals(95, micro.readFromMemory(15), 0);
	}

	@Test
	public void executeStrInstructionWithOutOfBoundsAddressAndFail() {
		micro.addInstruction(new Str(1024));
		try {
			micro.executeProgram();
			fail();
		} catch (IllegalValueException e) {
			assertEquals("Invalid Memory Address", e.getMessage());
		}
	}

	@Test
	public void executeStrInstructionWithNegativeAddressAndFail() {
		micro.addInstruction(new Str(-1));
		try {
			micro.executeProgram();
			fail();
		} catch (IllegalValueException e) {
			assertEquals("Invalid Memory Address", e.getMessage());
		}
	}

	@Test
	public void executeLodvInstruction() {
		micro.addInstruction(new Lodv(15));
		micro.executeProgram();
	}

	@Test
	public void executeLodvInstructionCheckingRegister() {
		micro.addInstruction(new Lodv(15));
		micro.executeProgram();
		assertEquals(15, micro.getRegister("A").getValue(), 0);
	}

	@Test
	public void executeLodvInstructionWithNegativeValueAndFail() {
		micro.addInstruction(new Lodv(-1));
		try {
			micro.executeProgram();
			fail();
		} catch (IllegalValueException e) {
			assertEquals("Negative Numbers are not Supported", e.getMessage());
		}
	}

	@Test
	public void executeLodvInstructionWithOutOfBoundsValueAndFail() {
		micro.addInstruction(new Lodv(256));
		try {
			micro.executeProgram();
			fail();
		} catch (IllegalValueException e) {
			assertEquals("Number Range Overflow", e.getMessage());
		}
	}
}
