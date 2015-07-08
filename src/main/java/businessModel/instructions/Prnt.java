package businessModel.instructions;

import businessModel.MicroController;

public class Prnt extends Instruction {

	public void execute(MicroController micro) {
		System.out.println(micro.getRegister("A").getValue());
		super.execute(micro);
	}
}
