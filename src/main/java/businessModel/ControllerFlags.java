package businessModel;

public class ControllerFlags {

	private Integer ip = 0;

	public Integer getIP() {
		return this.ip;
	}

	public void clear() {
		this.ip = 0;
	}

	public void updateIP() {
		this.ip++;
	}

}
