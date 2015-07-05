package businessModel;

import java.util.Hashtable;
import businessModel.state.ControllerState;
import businessModel.state.StopState;

public class MicroController {

	private Hashtable<String, Register> registers = new Hashtable<String, Register>();
	private MainMemory memory = new MainMemory(1024);
	private Program program = null;
	private ControllerState state = StopState.INSTANCE;
	private ControllerFlags flags = new ControllerFlags();

	public MicroController() {
		registers.put("A", new Register(1));
		registers.put("B", new Register(1));
	}

	public Register getRegister(String registerLetter) {
		return this.registers.get(registerLetter.toUpperCase());
	}

	public void setRegister(String registerLetter, Integer registerValue) {
		this.getRegister(registerLetter).setValue(registerValue);
	}

	public void setState(ControllerState state) {
		this.state = state;
	}

	public ControllerState getState() {
		return this.state;
	}

	public Program getProgram() {
		return this.program;
	}

	public void loadProgram(Program prog) {
		this.program = prog;
	}

	public ControllerFlags getFlags() {
		return this.flags;
	}

	public Integer readFromMemory(Integer memoryAddr) {
		return this.memory.getDataFrom(memoryAddr);
	}

	public void writeOnMemoryPosition(Integer position, Integer value) {
		this.memory.setDataOn(position, value);
	}

	public void clearMicro() {
		registers.get("A").setValue(0);
		registers.get("B").setValue(0);
		memory.cleanMemory();
		flags.clear();
	}

	public void load(Program program) {
		this.state.load(this, program);
	}

	public void start() {
		this.state.start(this);
	}

	public void execute() {
		this.state.execute(this);
	}

	public void step() {
		this.state.step(this);
	}

	public void stop() {
		this.state.stop(this);
	}
}
