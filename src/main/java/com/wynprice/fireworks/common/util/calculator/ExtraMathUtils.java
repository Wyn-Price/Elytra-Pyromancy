package com.wynprice.fireworks.common.util.calculator;

import com.sun.jna.platform.win32.WinDef.UINT_PTR;

/**
 * Util class to run extra math calculations
 * @author Wyn Price
 *
 */
public class ExtraMathUtils {
	
	/**
	 * Gets the nth root of a number.
	 * @param root the root to get from the number
	 * @param num the number to get the nth (defined by {@code root}) root of
	 * @return the 
	 */
	public static double root(double root, double num) {
		if(root == 0) {
			return 1;
		} else if (num < 0) {
	        return -Math.pow(Math.abs(num), (1 / root));
	    } else {
		    return Math.pow(num, 1.0 / root);
	    }
	}
	
	/**
	 * The charset with the values that can be used to represent based numbers greater than 10. <br>
	 * Is a list of 0-9, A-Z and a-z
	 */
	public static final String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	/**
	 * Parses the string argument as a signed double in the radix specified by the second argument.
	 * The characters in the string must all be digits of the specified radix 
	 * (as determined if the index of the character in {@link ExtraMathUtils#CHARSET} 
	 * is larger or equal to the {@code radix}}).  The first character may be an ASCII
	 * minus sign '-' ('\u002D') to indicate a negative value or an ASCII plus sign '+' ('\u002B') 
	 * to indicate a positive value. The resulting double value is returned. 
	 * @param input the String containing the double representation to be parsed
	 * @param radix the radix to be used while parsing {@code input}
	 * @return the double represented by the string argument in the specified radix.
	 * @throws MathReaderException if the string does not contain a parsable integer, if the radix is 0, 
	 * or if the input characters digit in the sting is larger or equal to the radix
	 */
	public static double parseDouble(String input, double radix) throws MathReaderException {
		if(input.startsWith("+")) {
			input = input.substring(1, input.length());
		}
		boolean isNegative = input.startsWith("-");
		if(isNegative){
			input = input.substring(1, input.length());
		}
		if(radix == 0) {
			throw new MathReaderException("Unable to process base conversion with radix 0");
		}
		int decimal = input.indexOf('.');
		if(decimal == -1) {
			decimal = input.length();
		} else {
			input = input.substring(0, decimal) + input.substring(decimal + 1, input.length());
		}
		double out = 0;
		for(int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			int charIndex = CHARSET.indexOf(c);
			if(charIndex >= radix) {
				throw new MathReaderException("Input character:" + c + " was larger than the radix: " + radix);
			}
 			if(charIndex == -1) {
				throw new MathReaderException("Unexpected Character: " + c + ", at position: " + i);
			}
			out += charIndex * Math.pow(radix, decimal - i - 1);
		}
		return isNegative ? -out : out;
	}
}
