package businessModel.instructions;

import businessModel.MicroController;

public class Lodv implements Instruction {

	private Integer value;

	public Lodv(Integer value) {
		this.value = value;
	}

	@Override
	public void execute(MicroController micro) {
		micro.setRegister("A", value);
	}

}
