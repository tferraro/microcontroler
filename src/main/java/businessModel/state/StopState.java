package businessModel.state;

import exceptions.IllegalOperationException;
import businessModel.MicroController;
import businessModel.Program;

public enum StopState implements ControllerState {
	INSTANCE;

	@Override
	public void load(MicroController micro, Program program) {
		if (program == null)
			throw new IllegalOperationException("Program cannot be null");
		micro.loadProgram(program);
		micro.setState(LoadState.INSTANCE);
	}

	@Override
	public void start(MicroController micro) {
		if (micro.getProgram() == null)
			throw new IllegalOperationException(
					"Program must be first loaded in memory");
		micro.setState(LoadState.INSTANCE);
		micro.start();
	}

}
