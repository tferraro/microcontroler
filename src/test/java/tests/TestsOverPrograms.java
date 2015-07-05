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
import businessModel.instructions.Swap;

public class TestsOverPrograms {

	private MicroController micro;

	@Before
	public void setUp() {
		micro = new MicroController();
	}

	@Test
	public void executeProgramAddNop() { // 100 + 0
		micro.setRegister("A", 100);
		micro.addInstruction(new Add());
		micro.addInstruction(new Nop());
		micro.executeProgram();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(100, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeProgram1() { // 100 + 50
		micro.addInstruction(new Lodv(100));
		micro.addInstruction(new Swap());
		micro.addInstruction(new Lodv(50));
		micro.addInstruction(new Add());
		micro.executeProgram();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(150, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeProgram2() { // 200 + 150
		micro.addInstruction(new Lodv(200));
		micro.addInstruction(new Swap());
		micro.addInstruction(new Lodv(150));
		micro.addInstruction(new Add());
		micro.executeProgram();
		assertEquals(95, micro.getRegister("A").getValue(), 0);
		assertEquals(255, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeProgram3() { // 2 / 0
		micro.addInstruction(new Lodv(0));
		micro.addInstruction(new Swap());
		micro.addInstruction(new Lodv(2));
		micro.addInstruction(new Div());
		try {
			micro.executeProgram();
		} catch (IllegalValueException e) {
			assertEquals("Zero Division", e.getMessage());
		}
	}

	@Test
	public void executeProgram4() { // 2 + 8 + 5
		micro.addInstruction(new Lodv(2));
		micro.addInstruction(new Str(0));
		micro.addInstruction(new Lodv(8));
		micro.addInstruction(new Swap());
		micro.addInstruction(new Lodv(5));
		micro.addInstruction(new Add());
		micro.addInstruction(new Lod(0));
		micro.addInstruction(new Add());
		micro.executeProgram();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(15, micro.getRegister("B").getValue(), 0);
	}

}
