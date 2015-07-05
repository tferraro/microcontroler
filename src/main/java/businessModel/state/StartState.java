package businessModel.state;

import exceptions.IllegalOperationException;
import businessModel.MicroController;
import businessModel.Program;

public enum StartState implements ControllerState {
	INSTANCE;

	@Override
	public void load(MicroController micro, Program program) {
		throw new IllegalOperationException("Program Must be Stopped properly first");
		
	}

	@Override
	public void start(MicroController micro) {
		// TODO Auto-generated method stub
		
	}

}
