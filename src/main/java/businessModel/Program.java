package businessModel;

import java.util.ArrayList;
import java.util.List;

import exceptions.FinishedExecutionException;
import businessModel.instructions.Instruction;

public class Program {

	private List<Instruction> instructions = new ArrayList<>();

	public void addInstruction(Instruction instruction) {
		this.instructions.add(instruction);
	}

	public void executeAll(MicroController micro) {
		while (micro.getFlags().getIP() < instructions.size()) {
			instructions.get(micro.getFlags().getIP()).execute(micro);
		}
	}

	public void execute(MicroController micro) {
		instructions.get(micro.getFlags().getIP()).execute(micro);
		if (micro.getFlags().getIP() == instructions.size())
			throw new FinishedExecutionException("Last Step Finished");
	}

}
