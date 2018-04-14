package com.wynprice.fireworks.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class NonIndentedPrintStream extends PrintStream {

	public static final NonIndentedPrintStream INSTANCE = new NonIndentedPrintStream();
	
	private NonIndentedPrintStream() {
		super(new NonIndentedOutputStream());
	}
	private static class NonIndentedOutputStream extends OutputStream {

		@Override
		public void write(int b) throws IOException {
			System.out.print((char)b);
		}
		
	}
}
