package com.wynprice.fireworks.common.util;

public class MathReader {//TODO: remove this class
	public static double eval(String string) {
		try {
			return new MathCalculation(string).getOutput();
		} catch (MathCalculation.MathReaderException e) {
			throw new RuntimeException(e);
		}
	}
}
