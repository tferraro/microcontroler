package businessModel.state;

import businessModel.MicroController;
import businessModel.Program;

public enum StopState implements ControllerState {
	INSTANCE;

	@Override
	public void load(MicroController micro, Program program) {
		micro.loadProgram(program);
		micro.setState(LoadState.INSTANCE);
	}

}
