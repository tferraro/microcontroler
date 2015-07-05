package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import exceptions.IllegalValueException;
import businessModel.MicroController;
import businessModel.Program;
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
	private Program prog1;

	@Before
	public void setUp() {
		micro = new MicroController();
		prog1 = new Program();
	}

	@Test
	public void executeNopInstruction() {
		prog1.addInstruction(new Nop());
		micro.load(prog1);
		micro.start();
		micro.execute();
	}

	@Test
	public void executeAddInstruction() {
		prog1.addInstruction(new Add());
		micro.load(prog1);
		micro.start();
		micro.execute();
	}

	@Test
	public void executeAddInstructionCheckingRegisters() {
		prog1.addInstruction(new Add());
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 100);
		micro.setRegister("B", 50);
		micro.execute();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(150, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeAddInstructionWithOverflowResultCheckingRegisters() {
		prog1.addInstruction(new Add());
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 150);
		micro.setRegister("B", 200);
		micro.execute();
		assertEquals(95, micro.getRegister("A").getValue(), 0);
		assertEquals(255, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeSubInstruction() {
		prog1.addInstruction(new Sub());
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 0);
		micro.setRegister("B", 50);
		micro.execute();
	}

	@Test
	public void executeSubInstructionCheckingRegisters() {
		prog1.addInstruction(new Sub());
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 50);
		micro.setRegister("B", 100);
		micro.execute();

		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(50, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeSubInstructionWithNegativeResultAndFail() {
		prog1.addInstruction(new Sub());
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 100);
		micro.setRegister("B", 0);
		try {
			micro.execute();
			fail();
		} catch (IllegalValueException e) {
			assertEquals("Negative Numbers are not Supported", e.getMessage());
		}
	}

	@Test
	public void executeDivInstruction() {
		prog1.addInstruction(new Div());
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 50);
		micro.setRegister("B", 100);
		micro.execute();
	}

	@Test
	public void executeDivInstructionCheckingRegisters() {
		prog1.addInstruction(new Div());
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 50);
		micro.setRegister("B", 100);
		micro.execute();

		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(2, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeDivInstructionCheckingTruncate() {
		prog1.addInstruction(new Div());
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 50);
		micro.setRegister("B", 99);
		micro.execute();

		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(1, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeDivInstructionZeroDivision() {
		prog1.addInstruction(new Div());
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 0);
		micro.setRegister("B", 99);
		;
		try {
			micro.execute();
			fail("No Zero Division Exception Throwed");
		} catch (IllegalValueException e) {
			assertEquals("Zero Division", e.getMessage());
		}
	}

	@Test
	public void executeSwapInstruction() {
		prog1.addInstruction(new Swap());
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 50);
		micro.setRegister("B", 100);
		micro.execute();
	}

	@Test
	public void executeSwapInstructionCheckingRegisters() {
		prog1.addInstruction(new Swap());
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 50);
		micro.setRegister("B", 100);
		micro.execute();

		assertEquals(100, micro.getRegister("A").getValue(), 0);
		assertEquals(50, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeLodInstruction() {
		prog1.addInstruction(new Lod(15));
		micro.load(prog1);
		micro.start();
		micro.execute();
	}

	@Test
	public void executeLodInstructionCheckingRegister() {
		prog1.addInstruction(new Lod(15));
		micro.load(prog1);
		micro.start();
		micro.writeOnMemoryPosition(15, 1);
		micro.execute();
		assertEquals(1, micro.getRegister("A").getValue(), 0);
	}

	@Test
	public void executeLodInstructionWithNegativeAddressAndFail() {
		prog1.addInstruction(new Lod(-1));
		micro.load(prog1);
		micro.start();
		try {
			micro.execute();
			fail("No Invalid Address Exception Throwed");
		} catch (IllegalValueException e) {
			assertEquals("Invalid Memory Address", e.getMessage());
		}
	}

	@Test
	public void executeLodInstructionWithOutOfBoundsAddressAndFail() {
		prog1.addInstruction(new Lod(1024));
		micro.load(prog1);
		micro.start();
		try {
			micro.execute();
			fail("No Invalid Address Exception Throwed");
		} catch (IllegalValueException e) {
			assertEquals("Invalid Memory Address", e.getMessage());
		}
	}

	@Test
	public void executeStrInstruction() {
		prog1.addInstruction(new Str(15));
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 95);
		micro.execute();
	}

	@Test
	public void executeStrInstructionCheckingMemory() {
		prog1.addInstruction(new Str(15));
		micro.load(prog1);
		micro.start();
		micro.setRegister("A", 95);
		micro.execute();

		assertEquals(95, micro.readFromMemory(15), 0);
	}

	@Test
	public void executeStrInstructionWithOutOfBoundsAddressAndFail() {
		prog1.addInstruction(new Str(1024));
		micro.load(prog1);
		micro.start();
		try {
			micro.execute();
			fail("No Invalid Address Exception Throwed");
		} catch (IllegalValueException e) {
			assertEquals("Invalid Memory Address", e.getMessage());
		}
	}

	@Test
	public void executeStrInstructionWithNegativeAddressAndFail() {
		prog1.addInstruction(new Str(-1));
		micro.load(prog1);
		micro.start();
		try {
			micro.execute();
			fail("No Invalid Address Exception Throwed");
		} catch (IllegalValueException e) {
			assertEquals("Invalid Memory Address", e.getMessage());
		}
	}

	@Test
	public void executeLodvInstruction() {
		prog1.addInstruction(new Lodv(15));
		micro.load(prog1);
		micro.start();
		micro.execute();
	}

	@Test
	public void executeLodvInstructionCheckingRegister() {
		prog1.addInstruction(new Lodv(15));
		micro.load(prog1);
		micro.start();
		micro.execute();
		assertEquals(15, micro.getRegister("A").getValue(), 0);
	}

	@Test
	public void executeLodvInstructionWithNegativeValueAndFail() {
		prog1.addInstruction(new Lodv(-1));
		micro.load(prog1);
		micro.start();
		try {
			micro.execute();
			fail("No Illegal Value Exception Throwed");
		} catch (IllegalValueException e) {
			assertEquals("Negative Numbers are not Supported", e.getMessage());
		}
	}

	@Test
	public void executeLodvInstructionWithOutOfBoundsValueAndFail() {
		prog1.addInstruction(new Lodv(256));
		micro.load(prog1);
		micro.start();
		try {
			micro.execute();
			fail("No Illegal Value Exception Throwed");
		} catch (IllegalValueException e) {
			assertEquals("Number Range Overflow", e.getMessage());
		}
	}
}
