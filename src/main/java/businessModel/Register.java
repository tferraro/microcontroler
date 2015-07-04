package businessModel;

public class Register {

	private Integer value = 0;
	private Integer limit;

	public Register(Integer bytesSize) {
		limit = bytesSize * 256;
	}

	public void setValue(Integer registerValue) {
		this.value = registerValue;
	}

	public Integer getValue() {
		return this.value;
	}

	public Integer getLimit() {
		return this.limit - 1;
	}

}
