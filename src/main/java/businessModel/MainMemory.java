package businessModel;

import exceptions.IllegalValueException;

public class MainMemory {

	private Integer[] data;
	private Integer memorySize;

	public MainMemory(Integer bytesSize) {
		this.memorySize = bytesSize;
		data = new Integer[bytesSize];
		cleanMemory();
	}

	public void cleanMemory() {
		for (int i = 0; i < memorySize; i++)
			data[i] = 0;
	}

	public Integer getDataFrom(Integer memoryAddr) {
		validateAddress(memoryAddr);
		return data[memoryAddr];
	}

	public void setDataOn(Integer memoryAddr, Integer value) {
		validateAddress(memoryAddr);
		data[memoryAddr] = value;
	}

	private void validateAddress(Integer memoryAddr) {
		if (memoryAddr < 0 || memoryAddr >= memorySize)
			throw new IllegalValueException("Invalid Memory Address");
	}
}
