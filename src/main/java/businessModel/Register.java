package businessModel;

import exceptions.IllegalValueException;

public class Register {

	private Integer value = 0;
	private Integer maxValue;

	public Register(Integer bytesSize) {
		maxValue = (bytesSize * 256) - 1;
	}

	public void setValue(Integer registerValue) {
		checkValueRange(registerValue);
		this.value = registerValue;
	}

	private void checkValueRange(Integer valueOnTest) {
		if (valueOnTest < 0)
			throw new IllegalValueException(
					"Negative Numbers are not Supported");
		if (valueOnTest > maxValue)
			throw new IllegalValueException("Number Range Overflow");
	}

	public Integer getValue() {
		return this.value;
	}

	public Integer getLimit() {
		return this.maxValue;
	}

}
