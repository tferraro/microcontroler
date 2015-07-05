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
import businessModel.instructions.Swap;

public class TestsOverPrograms {

	private MicroController micro;
	private Program prog;

	@Before
	public void setUp() {
		micro = new MicroController();
		prog = new Program();
	}

	@Test
	public void executeProgramAddNop() { // 100 + 0; ;
		prog.addInstruction(new Add());
		prog.addInstruction(new Nop());
		micro.load(prog);
		micro.start();
		micro.setRegister("A", 100);
		micro.execute();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(100, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeProgram1() { // 100 + 50
		prog.addInstruction(new Lodv(100));
		prog.addInstruction(new Swap());
		prog.addInstruction(new Lodv(50));
		prog.addInstruction(new Add());
		micro.load(prog);
		micro.start();
		micro.execute();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(150, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeProgram2() { // 200 + 150
		prog.addInstruction(new Lodv(200));
		prog.addInstruction(new Swap());
		prog.addInstruction(new Lodv(150));
		prog.addInstruction(new Add());
		micro.load(prog);
		micro.start();
		micro.execute();
		assertEquals(95, micro.getRegister("A").getValue(), 0);
		assertEquals(255, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeProgram3() { // 2 / 0
		prog.addInstruction(new Lodv(0));
		prog.addInstruction(new Swap());
		prog.addInstruction(new Lodv(2));
		prog.addInstruction(new Div());
		micro.load(prog);
		micro.start();
		try {
			micro.execute();
			fail("No Zero Division Exception");
		} catch (IllegalValueException e) {
			assertEquals("Zero Division", e.getMessage());
		}
	}

	@Test
	public void executeProgram4() { // 2 + 8 + 5
		prog.addInstruction(new Lodv(2));
		prog.addInstruction(new Str(0));
		prog.addInstruction(new Lodv(8));
		prog.addInstruction(new Swap());
		prog.addInstruction(new Lodv(5));
		prog.addInstruction(new Add());
		prog.addInstruction(new Lod(0));
		prog.addInstruction(new Add());
		micro.load(prog);
		micro.start();
		micro.execute();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(15, micro.getRegister("B").getValue(), 0);
	}

}
