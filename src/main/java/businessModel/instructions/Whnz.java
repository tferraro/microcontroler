package businessModel.instructions;

import businessModel.MicroController;

public class Whnz extends Instruction {

	private Integer goBack;

	public Whnz(Integer instrToGoBack) {
		this.goBack = instrToGoBack;
	}

	public void execute(MicroController micro) {
		if (micro.getRegister("A").getValue() != 0) {
			micro.updateIP(-goBack);
			micro.updateAmounRegExecuted();
		} else
			super.execute(micro);
	}
}
