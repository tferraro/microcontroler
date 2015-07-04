package businessModel.instructions;

import businessModel.MicroController;
import businessModel.Register;

public class Add implements Instruction {

	@Override
	public void execute(MicroController micro) {
		Register regA = micro.getRegister("A");
		Register regB = micro.getRegister("B");
		Integer result = regA.getValue() + regB.getValue();
		
		if (result > regB.getLimit()) {
			// Exists Overflow
			micro.setRegister("A", result - regB.getLimit());
			micro.setRegister("B", regB.getLimit());
		} else {
			micro.setRegister("A", 0);
			micro.setRegister("B", result);

		}
	}

}
