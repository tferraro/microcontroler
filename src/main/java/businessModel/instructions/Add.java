package businessModel.instructions;

import businessModel.MicroController;
import businessModel.Register;

public class Add implements Instruction {

	private MicroController micro;

	@Override
	public void execute(MicroController microArgument) {
		this.micro = microArgument;
		Register regA = micro.getRegister("A");
		Register regB = micro.getRegister("B");
		Integer result = regA.getValue() + regB.getValue();

		if (result > regB.getLimit()) // Overflow Exists
			setRegisters(result - regB.getLimit(), regB.getLimit());
		else
			setRegisters(0, result);
	}

	public void setRegisters(Integer regAvalue, Integer regBvalue) {
		micro.setRegister("A", regAvalue);
		micro.setRegister("B", regBvalue);
	}

}
