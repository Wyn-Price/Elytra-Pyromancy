package com.wynprice.fireworks.common.util.calculator;

public class ExtraMathUtils {
	public static double root(double root, double num) {

		if(root == 0) {
			return 1;
		}
	    if (num < 0) {
	        return -Math.pow(Math.abs(num), (1 / root));
	    }
	    return Math.pow(num, 1.0 / root);
	}
	
	public static final String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	public static double parseDouble(String input, double radix) throws MathReaderException {
		if(radix == 0) {
			throw new MathReaderException("Unable to process base conversion with root 0");
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
			if(charIndex == -1) {
				throw new MathReaderException("Unexpected Character: " + c + ", at position: " + i);
			}
			out += charIndex * Math.pow(radix, decimal - i - 1);
		}
		return out;
	}
}
