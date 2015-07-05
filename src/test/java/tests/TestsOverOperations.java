package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import exceptions.IllegalOperationException;
import businessModel.MicroController;
import businessModel.ProgramBuilder;
import businessModel.state.ExecuteState;
import businessModel.state.LoadState;
import businessModel.state.StartState;
import businessModel.state.StopState;

public class TestsOverOperations {

	private MicroController micro;

	@Before
	public void setUp() {
		micro = new MicroController();
	}

	@Test
	public void loadAProgramOnMicroUsingBuilder() {
		assertEquals(StopState.INSTANCE, micro.getState());
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		assertEquals(LoadState.INSTANCE, micro.getState());
	}

	@Test
	public void loadANullProgramOnMicroAndFail() {
		try {
			micro.load(null);
		} catch (IllegalOperationException e) {
			assertEquals(StopState.INSTANCE, micro.getState());
			assertEquals("Program cannot be null", e.getMessage());
		}
	}

	@Test
	public void loadAndStartProgram() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		assertEquals(StartState.INSTANCE, micro.getState());
	}

	@Test
	public void startWithNullProgramAndFail() {
		try {
			micro.start();
		} catch (IllegalOperationException e) {
			assertEquals(StopState.INSTANCE, micro.getState());
			assertEquals("Program cannot be null", e.getMessage());
		}
	}

	@Test
	public void tryToLoadAfterStartWIthouthStopFirstAndFail() {
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

	@Test
	public void tryDoubleStartAndFail() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		try {
			micro.start();
		} catch (IllegalOperationException e) {
			assertEquals("Program Already Started", e.getMessage());
		}
	}

	@Test
	public void loadAndExecuteWithouthStartingAndFail() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		try {
			micro.execute();
		} catch (IllegalOperationException e) {
			assertEquals("Program must be first started correctly",
					e.getMessage());
		}
	}

	@Test
	public void executeProgramCorrectly() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.execute();
	}

	@Test
	public void executeProgramCorrectlyCheckingState() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.execute();
		assertEquals(StopState.INSTANCE, micro.getState());
	}

	@Test
	public void executeProgramCorrectlyCheckingRegisters() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.execute();
		assertEquals(StopState.INSTANCE, micro.getState());
		assertEquals(20, micro.getRegister("A").getValue(), 0);
		assertEquals(0, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void executeProgramWithoutLoadAndFail() {
		try {
			micro.execute();
		} catch (IllegalOperationException e) {
			assertEquals("Program cannot be null", e.getMessage());
			assertEquals(StopState.INSTANCE, micro.getState());
		}
	}

	@Test
	public void executeProgramWithoutStartAndFail() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		try {
			micro.execute();
		} catch (IllegalOperationException e) {
			assertEquals("Program must be first started correctly",
					e.getMessage());
			assertEquals(LoadState.INSTANCE, micro.getState());
		}
	}

	@Test
	public void executeProgramFromStoppedLoadedProgram() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.execute();
		micro.execute();
		assertEquals(20, micro.getRegister("A").getValue(), 0);
		assertEquals(0, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void startAndCheckIPValue() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		assertEquals(0, micro.getIP(), 0);
	}

	@Test
	public void stepCheckingRegisters() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.step();
		assertEquals(15, micro.getRegister("A").getValue(), 0);
		assertEquals(0, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void tryToLoadWhileSteppingAndFail() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.step();
		try {
			micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add()
					.swap().build());
		} catch (IllegalOperationException e) {
			assertEquals("Program must be first stopped", e.getMessage());
			assertEquals(ExecuteState.INSTANCE, micro.getState());
		}
	}

	@Test
	public void tryToStartWhileSteppingAndFail() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.step();
		try {
			micro.start();
		} catch (IllegalOperationException e) {
			assertEquals("Program must be first stopped", e.getMessage());
			assertEquals(ExecuteState.INSTANCE, micro.getState());
		}
	}

	@Test
	public void stepTwiceCheckingRegisters() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.step();
		assertEquals(15, micro.getRegister("A").getValue(), 0);
		assertEquals(0, micro.getRegister("B").getValue(), 0);
		micro.step();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(15, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void stepTillEnd() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.step();
		micro.step();
		micro.step();
		micro.step();
		micro.step();
		assertEquals(20, micro.getRegister("A").getValue(), 0);
		assertEquals(0, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void stepTillEndAndOneMore() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.step();
		micro.step();
		micro.step();
		micro.step();
		micro.step();
		try {
			micro.step();
		} catch (IllegalOperationException e) {
			assertEquals("Program is stopped, must be first started to step",
					e.getMessage());
			assertEquals(StopState.INSTANCE, micro.getState());
		}
	}

	@Test
	public void tryStopWhileStoppedAndFail() {
		try {
			micro.stop();
			fail("No Exception Launched");
		} catch (IllegalOperationException e) {
			assertEquals("Program is already stopped", e.getMessage());
		}
	}

	@Test
	public void tryStopWhileLoadedAndFail() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		try {
			micro.stop();
			fail("No Exception Launched");
		} catch (IllegalOperationException e) {
			assertEquals("Program is already stopped", e.getMessage());
		}
	}

	@Test
	public void stopWhileStartedCheckingState() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.stop();
		assertEquals(StopState.INSTANCE, micro.getState());
	}

	@Test
	public void stopWhileSteppingCheckingState() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.step();
		micro.stop();
		assertEquals(StopState.INSTANCE, micro.getState());
	}

	@Test
	public void stopAfterExecutingCheckingState() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.execute();
		try {
			micro.stop();
			fail("No Exception Launched");
		} catch (IllegalOperationException e) {
			assertEquals("Program is already stopped", e.getMessage());
		}
	}

	@Test
	public void stepTillEndAndExecuteOnce() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.step();
		micro.step();
		micro.step();
		micro.step();
		micro.step();
		micro.execute();
	}

	@Test
	public void executeFromMiddleOfStepCheckingRegistersAndState() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.step();
		micro.step();
		micro.execute();
		assertEquals(20, micro.getRegister("A").getValue(), 0);
		assertEquals(0, micro.getRegister("B").getValue(), 0);
		assertEquals(StopState.INSTANCE, micro.getState());
	}

	@Test
	public void stepBackWithoutLoadAndFail() {
		try {
			micro.stepBack();
			fail("No Exception Launched");
		} catch (IllegalOperationException e) {
			assertEquals("Program is stopped, must be first started to step",
					e.getMessage());
		}

	}

	@Test
	public void stepBackWithoutStartingAndFail() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		try {
			micro.stepBack();
			fail("No Exception Launched");
		} catch (IllegalOperationException e) {
			assertEquals("Program must be first started correctly",
					e.getMessage());
		}

	}

	@Test
	public void stepBackWithoutSteppingAndFail() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		try {
			micro.stepBack();
			fail("No Exception Launched");
		} catch (IllegalOperationException e) {
			assertEquals("Program must be first stepped", e.getMessage());
		}
	}

	@Test
	public void stepBackLoadv() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.step();
		assertEquals(15, micro.getRegister("A").getValue(), 0);
		assertEquals(0, micro.getRegister("B").getValue(), 0);
		micro.stepBack();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(0, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void stepBackCheckingRegisterValues() {
		micro.load(new ProgramBuilder().lodv(15).swap().lodv(5).add().swap()
				.build());
		micro.start();
		micro.step();
		micro.step();
		micro.step();
		micro.step();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(20, micro.getRegister("B").getValue(), 0);
		micro.stepBack();
		micro.stepBack();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(15, micro.getRegister("B").getValue(), 0);
		micro.stepBack();
		micro.stepBack();
		assertEquals(0, micro.getRegister("A").getValue(), 0);
		assertEquals(0, micro.getRegister("B").getValue(), 0);
	}

	@Test
	public void stepBackCheckingMemoryValue() {
		micro.load(new ProgramBuilder().lodv(15).str(0).swap().build());
		micro.start();
		micro.step();
		micro.step();
		assertEquals(15, micro.readFromMemory(0), 0);
		micro.stepBack();
		assertEquals(0, micro.readFromMemory(0), 0);
	}
}
