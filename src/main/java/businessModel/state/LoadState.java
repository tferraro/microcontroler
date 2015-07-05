package businessModel.state;

import exceptions.IllegalOperationException;
import businessModel.MicroController;
import businessModel.Program;

public enum LoadState implements ControllerState {
	INSTANCE;

	@Override
	public void load(MicroController micro, Program program) {
		micro.loadProgram(program);
	}

	@Override
	public void start(MicroController micro) {
		micro.clearMicro();
		micro.setState(StartState.INSTANCE);
	}

	@Override
	public void execute(MicroController micro) {
		throw new IllegalOperationException(
				"Program must be first started correctly");
	}

	@Override
	public void step(MicroController micro) {
		throw new IllegalOperationException(
				"Program must be first started correctly");
	}

	@Override
	public void stop(MicroController micro) {
		throw new IllegalOperationException("Program is already stopped");
	}

}
