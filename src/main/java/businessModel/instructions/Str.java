package businessModel.instructions;

import businessModel.MicroController;

public class Str implements Instruction {

	private Integer memoryAddr;

	public Str(Integer addr) {
		this.memoryAddr = addr;
	}

	@Override
	public void execute(MicroController micro) {
		micro.writeOnMemoryPosition(memoryAddr, micro.getRegister("A")
				.getValue());
	}

}
