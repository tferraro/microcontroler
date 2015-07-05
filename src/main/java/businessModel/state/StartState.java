package businessModel.state;

import exceptions.IllegalOperationException;
import businessModel.MicroController;
import businessModel.Program;

public enum StartState implements ControllerState {
	INSTANCE;

	@Override
	public void load(MicroController micro, Program program) {
		throw new IllegalOperationException(
				"Program Must be Stopped properly first");
	}

	@Override
	public void start(MicroController micro) {
		throw new IllegalOperationException("Program Already Started");
	}

	@Override
	public void execute(MicroController micro) {
		micro.setState(ExecuteState.INSTANCE);
		micro.execute();
	}

	@Override
	public void step(MicroController micro) {
		micro.setState(ExecuteState.INSTANCE);
		micro.step();
	}

	@Override
	public void stop(MicroController micro) {
		micro.setState(StopState.INSTANCE);
	}

	@Override
	public void stepBack(MicroController microController) {
		throw new IllegalOperationException("Program must be first stepped");
	}

}
