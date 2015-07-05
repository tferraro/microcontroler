package businessModel;

import java.util.Hashtable;

import businessModel.instructions.Instruction;

public class MicroController {

	private Instruction instruction;
	private Hashtable<String, Register> registers = new Hashtable<String, Register>();
	private MainMemory memory = new MainMemory(1024);

	public MicroController() {
		registers.put("A", new Register(1));
		registers.put("B", new Register(1));
	}

	public void addInstruction(Instruction instruc) {
		this.instruction = instruc;
	}

	public void executeProgram() {
		instruction.execute(this);
	}

	public Register getRegister(String registerLetter) {
		return this.registers.get(registerLetter.toUpperCase());
	}

	public void setRegister(String registerLetter, Integer registerValue) {
		this.getRegister(registerLetter).setValue(registerValue);
	}

	public Integer readFromMemory(Integer memoryAddr) {
		return this.memory.getDataFrom(memoryAddr);
	}

	public void writeOnMemoryPosition(Integer position, Integer value) {
		this.memory.setDataOn(position, value);
	}

}
