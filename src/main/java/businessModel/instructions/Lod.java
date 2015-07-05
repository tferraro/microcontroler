package businessModel.instructions;

import businessModel.MicroController;

public class Lod implements Instruction {

	private Integer memoryAddr;

	public Lod(Integer addr) {
		this.memoryAddr = addr;
	}

	@Override
	public void execute(MicroController micro) {
		Integer value = micro.readFromMemory(memoryAddr);
		micro.setRegister("A", value);
	}

}
