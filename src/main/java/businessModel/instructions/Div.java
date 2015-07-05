package businessModel.instructions;

import java.math.BigDecimal;
import java.math.RoundingMode;

import exceptions.IllegalValueException;
import businessModel.MicroController;

public class Div implements Instruction {

	@Override
	public void execute(MicroController micro) {
		BigDecimal regAValue = new BigDecimal(micro.getRegister("A").getValue());
		BigDecimal regBValue = new BigDecimal(micro.getRegister("B").getValue());

		Integer result;
		try {
			result = regBValue.divide(regAValue, RoundingMode.DOWN).intValue();
			micro.setRegister("A", 0);
			micro.setRegister("B", result);
		} catch (ArithmeticException launchedExep) {
			if (launchedExep.getMessage().matches("/ by zero"))
				throw new IllegalValueException("Zero Division");
			throw launchedExep;
		}
	}

}
