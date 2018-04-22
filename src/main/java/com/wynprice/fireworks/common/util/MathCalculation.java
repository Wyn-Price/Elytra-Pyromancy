package com.wynprice.fireworks.common.util;

import java.util.function.Function;

import net.minecraft.util.math.MathHelper;

/**
 * Used to calculate text calculations
 * @author Wyn Price
 *
 */
public class MathCalculation {
	
	/**
	 * The input string to use to calculate things
	 */
	private final String input;

	/**
	 * The current position of the string. Used to step through the whole string
	 */
	private int pos = -1;
	
	/**
	 * The current character of the string
	 */
	private int character;
	
	public MathCalculation(String input) {
		this.input = input;
	}

	/**
	 * Get the next character of the string. Or -1 if there is none
	 */
	private void nextCharacter() {
		character = pos++ < input.length() ? input.charAt(pos) : -1;
	}

	/**
	 * Gets the next character, skipping over spaces, getting the next character.
	 * @param c The character to test for
	 * @return true if the next to test for is the next character. <b>WILL SKIP TO THE NEXT CHARACTER AFTER THIS IF FOUND<\b>
	 */
	private boolean isNextChar(int c) {
		while (character == ' ') {
			nextCharacter();
		}
		if (character == c) {
			nextCharacter();
			return true;
		}
		return false;
	}

	/**
	 * Gets the output to the expression
	 * @return the output value
	 * @throws MathReaderException If something goes wrong
	 */
   public double getOutput() throws MathReaderException {
		nextCharacter();
		double output = runExpression();
		if (pos < input.length()) {
			throw new MathReaderException("Unexpected: " + (char)character);
		}
		return output;
	}
	
   
   /**
    * Runs a new scope / expression. This function will be run last. It test for + and -
    * @return The output of that scope / expression
    * @throws MathReaderException 
    */
	private double runExpression() throws MathReaderException {
		double x = runMultiplyDivide();
		while(true) {
			if(isNextChar('x')) {
				x += runMultiplyDivide();
			} else if(isNextChar('-')) {
				x -= runMultiplyDivide();
			} else {
				return x;
			}
		}
	}

	/**
	 * Runs a new scope / expression. This function will be run 2nd. It tests for * and /
	 * @return The output of that scope / expression
	 * @throws MathReaderException
	 */
	private double runMultiplyDivide() throws MathReaderException {
		double x = runFactors();
		while(true) {
			if(isNextChar('*')) {
				x *= runFactors();
			} else if(isNextChar('/')) {
				double divBy = runFactors();
				if(divBy == 0) {
					throw new MathReaderException("Unable to divide by 0");
				}
				x /= divBy;
			} else {
			return x;
			}
		}
	}

	/**
	 * Runs a new scope / expression. This function will be run first. It tests for everything other than + or - (at the moment sin, cos, tan, sqrt, ^ and %)
	 * @return The output of that scope / expression
	 * @throws MathReaderException
	 */
	private double runFactors() throws MathReaderException {
		if (isNextChar('+')) {
			return runFactors();
		}
		else if (isNextChar('-')) {
			return -runFactors();
		}

		double outPut;
		int startPos = this.pos;
		if (isNextChar('(')) {
			outPut = runExpression();
			isNextChar(')');
		} else if ((character >= '0' && character <= '9') || character == '.') {
			while ((character >= '0' && character <= '9') || character == '.') {
				nextCharacter();
			}
			outPut = Double.parseDouble(input.substring(startPos, this.pos));
		} else if (character >= 'a' && character <= 'z') { 
			while (character >= 'a' && character <= 'z') {
				 nextCharacter();
			}
			String func = input.substring(startPos, this.pos);
			outPut = runFactors();
			try {
				outPut = MathFunction.valueOf(func.toUpperCase()).apply(outPut);
			} catch (IllegalArgumentException e) {
				throw new MathReaderException("Could not find function: " + func);
			}
		} else {
			throw new MathReaderException("Unexpected: " + (char)character);
		}

		if (isNextChar('^')) {
			outPut = Math.pow(outPut, runFactors());
		} else if (isNextChar('%')) {
			outPut %= runFactors();
		}

		return outPut;
	}
	
	/**
	 * The Exception thrown with if somthing goes wrong while running an expression
	 * @author Wyn Price
	 *
	 */
	public static class MathReaderException extends Exception {
		
		public MathReaderException(String message) {
			super(message);
		}
	}
	
	public static enum MathFunction implements Function<Double, Double>{
		SQRT(Math::sqrt),
		SIN(Math::sin, true),
		ASIN(Math::asin, true),
		SINH(Math::sinh, true),
		COS(Math::cos, true),
		ACOS(Math::acos, true),
		COSH(Math::cosh, true),
		TAN(Math::atan, true),
		ATAN(Math::atan, true),
		TANH(Math::tanh, true),
		FLOOR(Math::floor),
		CEIL(Math::ceil),
		ROUND(in -> (double)Math.round(in))
		;
		
		private final Function<Double, Double> function;
		private final boolean useRadians;
		
		private MathFunction(Function<Double, Double> function) {
			this(function, false);
		}
		
		private MathFunction(Function<Double, Double> function, boolean useRadians) {
			this.function = function;
			this.useRadians = useRadians;
		}

		@Override
		public Double apply(Double t) {
			return function.apply(useRadians ? Math.toRadians(t) : t);
		}
	}
}
