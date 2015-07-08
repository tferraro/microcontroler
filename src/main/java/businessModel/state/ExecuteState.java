package businessModel.state;

import exceptions.FinishedExecutionException;
import exceptions.IllegalOperationException;
import businessModel.MicroController;
import businessModel.Program;

public enum ExecuteState implements ControllerState {
	INSTANCE;

	@Override
	public void load(MicroController micro, Program program) {
		throw new IllegalOperationException("Program must be first stopped");
	}

	@Override
	public void start(MicroController micro) {
		throw new IllegalOperationException("Program must be first stopped");
	}

	@Override
	public void execute(MicroController micro) {
		try {
			micro.getProgram().executeAll(micro);
			this.stop(micro);
		} catch (FinishedExecutionException e) {

		}
	}

	@Override
	public void step(MicroController micro) {
		try {
			micro.getProgram().execute(micro);
		} catch (FinishedExecutionException e) {
			if (e.getMessage().equals("Last Step Finished"))
				this.stop(micro);
		}
	}

	@Override
	public void stop(MicroController micro) {
		micro.setState(StopState.INSTANCE);
	}

	@Override
	public void stepBack(MicroController micro) {
		// Do Nuthing
	}

}
