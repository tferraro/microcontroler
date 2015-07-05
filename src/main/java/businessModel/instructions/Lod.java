package businessModel.instructions;

import businessModel.MicroController;

public class Lod extends Instruction {

	private Integer memoryAddr;

	public Lod(Integer addr) {
		this.memoryAddr = addr;
	}

	public void execute(MicroController micro) {
		Integer value = micro.readFromMemory(memoryAddr);
		micro.setRegister("A", value);
		micro.setRegister("B");
		super.execute(micro);
	}

}
