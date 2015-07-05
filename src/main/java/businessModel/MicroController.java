package businessModel;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import businessModel.instructions.Instruction;
import businessModel.state.ControllerState;
import businessModel.state.LoadState;
import businessModel.state.StopState;

public class MicroController {

	@Deprecated
	private List<Instruction> instructionSet = new ArrayList<Instruction>();
	private Hashtable<String, Register> registers = new Hashtable<String, Register>();
	private MainMemory memory = new MainMemory(1024);
	private Program program = null;
	private ControllerState state = StopState.INSTANCE;

	public MicroController() {
		registers.put("A", new Register(1));
		registers.put("B", new Register(1));
	}

	@Deprecated
	public void addInstruction(Instruction instruc) {
		this.instructionSet.add(instruc);
	}

	@Deprecated
	public void executeProgram() {
		instructionSet.forEach(instr -> instr.execute(this));
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

	public void load(Program program) {
		this.state.load(this, program);
	}

	public void loadProgram(Program prog) {
		this.program = prog;
	}

	public void setState(ControllerState state) {
		this.state = state;
	}

	public ControllerState getState() {
		return this.state;
	}

}
