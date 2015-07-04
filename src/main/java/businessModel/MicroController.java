package businessModel;

import java.util.Hashtable;

import businessModel.instructions.Instruction;

public class MicroController {

	private Instruction instruction;
	private Hashtable<String, Register> registers = new Hashtable<String, Register>();

	public MicroController() {
		registers.put("A", new Register());
		registers.put("B", new Register());
	}

	public void addInstruction(Instruction instruc) {
		this.instruction = instruc;
	}

	public void executeProgram() {
		instruction.execute(this);
	}

	public Register getRegister(String registerLetter) {
		return this.registers.get(registerLetter);
	}

	public void setRegister(String registerLetter, Integer registerValue) {
		this.registers.get(registerLetter).setValue(registerValue);
	}

}
