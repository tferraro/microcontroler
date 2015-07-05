package businessModel.instructions;

import businessModel.MicroController;

public class Lodv extends Instruction {

	private Integer value;

	public Lodv(Integer value) {
		this.value = value;
	}

	public void execute(MicroController micro) {
		micro.setRegister("A", value);
		super.execute(micro);
	}

}
