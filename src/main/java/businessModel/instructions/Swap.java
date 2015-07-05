package businessModel.instructions;

import businessModel.MicroController;

public class Swap implements Instruction {

	@Override
	public void execute(MicroController micro) {
		Integer regAvalue = micro.getRegister("A").getValue();
		Integer regBvalue = micro.getRegister("B").getValue();
		
		micro.setRegister("A", regBvalue);
		micro.setRegister("B", regAvalue);
	}

}
