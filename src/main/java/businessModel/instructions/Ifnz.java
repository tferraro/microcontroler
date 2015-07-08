package businessModel.instructions;

import exceptions.IllegalValueException;
import businessModel.MicroController;

public class Ifnz extends Instruction {

	private Integer amount;

	public Ifnz(Integer amountInstructionsIf) {
		if(amountInstructionsIf <= 0)
			throw new IllegalValueException("Negative and Zero Values not supported.");
		this.amount = amountInstructionsIf;
	}

	public void execute(MicroController micro) {
		if (micro.getRegister("A").getValue() == 0) {
			micro.updateIP(amount + 1);
			micro.updateAmounRegExecuted();
		} else
			super.execute(micro);
	}
}
