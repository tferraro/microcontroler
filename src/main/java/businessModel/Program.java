package businessModel;

import java.util.ArrayList;
import java.util.List;

import businessModel.instructions.Instruction;

public class Program {

	private List<Instruction> instructions = new ArrayList<>();

	public void addInstruction(Instruction instruction) {
		this.instructions.add(instruction);
	}

}
