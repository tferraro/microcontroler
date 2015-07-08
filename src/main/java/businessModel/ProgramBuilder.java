package businessModel;

import java.util.ArrayList;
import java.util.List;

import businessModel.instructions.Add;
import businessModel.instructions.Div;
import businessModel.instructions.Ifnz;
import businessModel.instructions.Instruction;
import businessModel.instructions.Lod;
import businessModel.instructions.Lodv;
import businessModel.instructions.Nop;
import businessModel.instructions.Str;
import businessModel.instructions.Sub;
import businessModel.instructions.Swap;

public class ProgramBuilder {

	private List<Instruction> instructions = new ArrayList<>();

	public ProgramBuilder add() {
		this.instructions.add(new Add());
		return this;
	}

	public ProgramBuilder sub() {
		this.instructions.add(new Sub());
		return this;
	}

	public ProgramBuilder div() {
		this.instructions.add(new Div());
		return this;
	}

	public ProgramBuilder swap() {
		this.instructions.add(new Swap());
		return this;
	}

	public ProgramBuilder nop() {
		this.instructions.add(new Nop());
		return this;
	}

	public ProgramBuilder lodv(Integer value) {
		this.instructions.add(new Lodv(value));
		return this;
	}

	public ProgramBuilder lod(Integer addr) {
		this.instructions.add(new Lod(addr));
		return this;
	}

	public ProgramBuilder str(Integer addr) {
		this.instructions.add(new Str(addr));
		return this;
	}

	public ProgramBuilder ifnz(Integer instr) {
		this.instructions.add(new Ifnz(instr));
		return this;
	}

	public Program build() {
		Program newProgram = new Program();
		instructions.forEach(instruction -> newProgram
				.addInstruction(instruction));
		return newProgram;
	}
}
