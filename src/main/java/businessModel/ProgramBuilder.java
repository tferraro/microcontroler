package businessModel;

import java.util.ArrayList;
import java.util.List;

import businessModel.instructions.Add;
import businessModel.instructions.Instruction;
import businessModel.instructions.Lodv;
import businessModel.instructions.Nop;
import businessModel.instructions.Swap;

public class ProgramBuilder {

	private List<Instruction> instructions = new ArrayList<>();

	public ProgramBuilder add() {
		this.instructions.add(new Add());
		return this;
	}

	public ProgramBuilder lodv(Integer value) {
		this.instructions.add(new Lodv(value));
		return this;
	}

	public ProgramBuilder swap() {
		this.instructions.add(new Swap());
		return this;
	}

	public Program build() {
		Program newProgram = new Program();
		instructions.forEach(instruction -> newProgram
				.addInstruction(instruction));
		return newProgram;
	}

	public ProgramBuilder nop() {
		this.instructions.add(new Nop());
		return this;
	}

}
