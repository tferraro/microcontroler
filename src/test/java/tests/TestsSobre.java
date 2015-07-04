package tests;

import org.junit.Test;

import businessModel.MicroController;
import businessModel.Register;
import businessModel.instructions.Add;
import businessModel.instructions.Instruction;
import businessModel.instructions.Nop;

public class TestsSobre {

	@Test
	public void executeNopInstruction() {
		MicroController micro = new MicroController();
		Instruction inst1 = new Nop();
		micro.addInstruction(inst1);
		micro.executeProgram();
	}

	@Test
	public void executeAddInstruction() {
		MicroController micro = new MicroController();
		Instruction inst1 = new Add();
		micro.addInstruction(inst1);
		micro.executeProgram();
	}
	
	@Test
	public void foo() {
		MicroController micro = new MicroController();
		micro.setRegister("A", 100);
		micro.setRegister("B", 50);
		Instruction inst1 = new Add();
		micro.addInstruction(inst1);
		micro.executeProgram();
		Register regA = micro.getRegister("A");
		Register regB = micro.getRegister("B");
		
	}

}
