package businessModel.state;

import exceptions.IllegalOperationException;
import businessModel.MicroController;
import businessModel.Program;

public enum StopState implements ControllerState {
	INSTANCE;

	@Override
	public void load(MicroController micro, Program program) {
		micro.loadProgram(program);
		checkLoad(micro);
		micro.setState(LoadState.INSTANCE);
	}

	@Override
	public void start(MicroController micro) {
		checkLoad(micro);
		micro.setState(LoadState.INSTANCE);
		micro.start();
	}

	@Override
	public void execute(MicroController micro) {
		checkLoad(micro);
		micro.setState(ExecuteState.INSTANCE);
		micro.execute();
	}

	@Override
	public void step(MicroController micro) {
		throw new IllegalOperationException(
				"Program is stopped, must be first started to step");
	}

	@Override
	public void stop(MicroController micro) {
		throw new IllegalOperationException("Program is already stopped");
	}

	private void checkLoad(MicroController micro) {
		if (micro.getProgram() == null)
			throw new IllegalOperationException("Program cannot be null");
	}

}
