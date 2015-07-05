package businessModel;

import java.util.ArrayList;
import java.util.List;

import exceptions.IllegalValueException;

public class MainMemory {

	private List<List<Integer>> data = new ArrayList<List<Integer>>();
	private Integer memorySize;
	private Integer amountMemAccess = 0;

	public MainMemory(Integer bytesSize) {
		this.memorySize = bytesSize;
		cleanMemory();
	}

	public void cleanMemory() {
		data.clear();
		for (int i = 0; i < memorySize; i++) {
			data.add(new ArrayList<Integer>());
			data.get(i).add(0);
		}
	}

	public Integer getDataFrom(Integer memoryAddr) {
		validateAddress(memoryAddr);
		List<Integer> memoryPos = data.get(memoryAddr);
		return memoryPos.get(memoryPos.size() - 1);
	}

	public void setDataOn(Integer memoryAddr, Integer value) {
		validateAddress(memoryAddr);
		data.get(memoryAddr).add(value);
		amountMemAccess++;
	}

	private void validateAddress(Integer memoryAddr) {
		if (memoryAddr < 0 || memoryAddr >= memorySize)
			throw new IllegalValueException("Invalid Memory Address");
	}

	public void setBack() {
		data.forEach(addr -> rollback(addr));

	}

	private void rollback(List<Integer> addr) {
		Integer acum = addr.size() - 1;
		if (amountMemAccess == acum)
			if (acum > 0)
				addr.remove((int) acum);
		amountMemAccess--;
	}
}
