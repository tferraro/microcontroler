package businessModel;

import java.util.ArrayList;
import java.util.List;

import exceptions.IllegalValueException;

public class Register {

	private List<Integer> value = new ArrayList<>();
	private Integer maxValue;

	public Register(Integer bytesSize) {
		maxValue = (bytesSize * 256) - 1;
		this.reset();
	}

	public void setValue(Integer registerValue) {
		checkValueRange(registerValue);
		this.value.add(registerValue);
	}

	private void checkValueRange(Integer valueOnTest) {
		if (valueOnTest < 0)
			throw new IllegalValueException(
					"Negative Numbers are not Supported");
		if (valueOnTest > maxValue)
			throw new IllegalValueException("Number Range Overflow");
	}

	public Integer getValue() {
		return this.value.get(value.size() - 1);
	}

	public Integer getLimit() {
		return this.maxValue;
	}

	public void reset() {
		this.value.clear();
		value.add(0);
	}

	public void setBack(Integer amountInstExecuted) {
		Integer acum = value.size() - 1;
		if (amountInstExecuted == acum)
			if (acum > 0)
				this.value.remove((int) acum);
	}

}
