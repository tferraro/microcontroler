package businessModel.instructions;

import businessModel.MicroController;

public class Str extends Instruction {

	private Integer memoryAddr;

	public Str(Integer addr) {
		this.memoryAddr = addr;
	}

	public void execute(MicroController micro) {
		micro.writeOnMemoryPosition(memoryAddr, micro.getRegister("A")
				.getValue());
		super.execute(micro);
	}

}
