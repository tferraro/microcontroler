package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.IllegalOperationException;
import businessModel.MicroController;
import businessModel.ProgramBuilder;
import businessModel.state.LoadState;
import businessModel.state.StartState;
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

	@Test
	public void loadANullProgramOnMicroAndFail() {
		MicroController micro = new MicroController();
		try {
			micro.load(null);
		} catch (IllegalOperationException e) {
			assertEquals(StopState.INSTANCE, micro.getState());
			assertEquals("Program cannot be null", e.getMessage());
		}
	}

	@Test
	public void loadAndStartProgram() {
		MicroController micro = new MicroController();
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		assertEquals(StartState.INSTANCE, micro.getState());
	}

	@Test
	public void startWithNullProgram() {
		MicroController micro = new MicroController();
		try {
			micro.start();
		} catch (IllegalOperationException e) {
			assertEquals(StopState.INSTANCE, micro.getState());
			assertEquals("Program must be first loaded in memory",
					e.getMessage());
		}
	}

	@Test
	public void tryToLoadAfterStartWIthouthStopFirst() {
		MicroController micro = new MicroController();
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		try {
			micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add()
					.swap().build());
		} catch (IllegalOperationException e) {
			assertEquals("Program Must be Stopped properly first",
					e.getMessage());
		}
	}

}
