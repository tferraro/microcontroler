package businessModel.instructions;

import exceptions.IllegalValueException;
import businessModel.MicroController;

public class Whnz extends Instruction {

	private Integer goBack;

	public Whnz(Integer instrToGoBack) {
		if (instrToGoBack <= 0)
			throw new IllegalValueException(
					"Negative and Zero Values not supported.");
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
