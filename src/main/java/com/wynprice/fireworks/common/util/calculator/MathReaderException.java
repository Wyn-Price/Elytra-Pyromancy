package com.wynprice.fireworks.common.util.calculator;

/**
 * The Exception thrown with if something goes wrong while running an expression
 * @author Wyn Price
 *
 */
public class MathReaderException extends Exception {
	public MathReaderException(String message) {
		super(message);
	}
	
	/**
	 * The Exception thrown when there is a syntax exception
	 * @author Wyn Price
	 *
	 */
	public static class MathReaderSyntaxException extends MathReaderException {
	
		public MathReaderSyntaxException(char expected, char got) {
			this(expected, String.valueOf(got));
		}
		
		public MathReaderSyntaxException(char expected, String got) {
			super("Invalid Syntax. Expected " + expected + ", got " + got);
		}
	}
}