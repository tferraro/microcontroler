package businessModel.instructions;

import businessModel.MicroController;

public class Sub extends Instruction {

	public void execute(MicroController micro) {
		Integer result = micro.getRegister("B").getValue()
				- micro.getRegister("A").getValue();
		micro.setRegister("A", 0);
		micro.setRegister("B", result);
		super.execute(micro);
	}

}
