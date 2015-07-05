package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import exceptions.IllegalValueException;
import businessModel.MicroController;

public class TestsOverMicroController {

	private MicroController micro;

	@Before
	public void setUp() {
		micro = new MicroController();
	}

	@Test
	public void getRegisterFromMicroControllerUsingLowerCaseLetter() {
		assertEquals(0, micro.getRegister("a").getValue(), 0);
	}

	@Test
	public void setRegisterFromMicroControllerUsingLowerCaseLetter() {
		micro.setRegister("a", 15);
		assertEquals(15, micro.getRegister("A").getValue(), 0);
	}

	@Test
	public void setRegisterWithNegativeAndFail() {
		try {
			micro.setRegister("A", -1);
			fail();
		} catch (IllegalValueException e) {
			assertEquals("Negative Numbers are not Supported", e.getMessage());
		}
	}

	@Test
	public void setRegisterWithOutOfBoundsValueAndFail() {
		try {
			micro.setRegister("A", 256);
			fail();
		} catch (IllegalValueException e) {
			assertEquals("Number Range Overflow", e.getMessage());
		}
	}
}
