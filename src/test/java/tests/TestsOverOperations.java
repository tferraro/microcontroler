package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import businessModel.MicroController;
import businessModel.Program;
import businessModel.ProgramBuilder;
import businessModel.instructions.Add;
import businessModel.state.LoadState;
import businessModel.state.StopState;

public class TestsOverOperations {

	@Test
	public void loadAProgramOnMicroUsingBuilder() {
		MicroController micro = new MicroController();
		assertEquals(StopState.INSTANCE, micro.getState());
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		assertEquals(LoadState.INSTANCE, micro.getState());
	}

}
