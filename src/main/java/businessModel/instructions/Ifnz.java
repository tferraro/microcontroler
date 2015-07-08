package businessModel.instructions;

import businessModel.MicroController;

public class Ifnz extends Instruction {

	private Integer amount;

	public Ifnz(Integer amountInstructionsIf) {

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
