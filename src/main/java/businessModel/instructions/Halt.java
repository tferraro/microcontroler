package businessModel.instructions;

import exceptions.FinishedExecutionException;
import businessModel.MicroController;

public class Halt extends Instruction {
	public void execute(MicroController micro) {
		super.execute(micro);
		throw new FinishedExecutionException("Halt Detected");
	}
}
