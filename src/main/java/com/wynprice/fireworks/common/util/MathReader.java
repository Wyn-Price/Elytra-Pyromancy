package com.wynprice.fireworks.common.util;

import com.wynprice.fireworks.common.util.calculator.MathCalculation;
import com.wynprice.fireworks.common.util.calculator.MathReaderException;

public class MathReader {//TODO: remove this class
	public static double eval(String string) {
		try {
			return new MathCalculation(string).getOutput();
		} catch (MathReaderException e) {
			throw new RuntimeException(e);
		}
	}
}
