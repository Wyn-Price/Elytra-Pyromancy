package com.wynprice.fireworks.common.util.calculator;

import java.util.function.BiFunction;

public enum DoubleMathFunctions {
	ROOT(ExtraMathUtils::root),
	MAX(Math::max),
	MIN(Math::min),
	HYPOT(Math::hypot),
	IEEEREMAINDER(Math::IEEEremainder),;

	private final BiFunction<Double, Double, Double> function;
	
	private DoubleMathFunctions(BiFunction<Double, Double, Double> function) {
		this.function = function;
	}
	
	public BiFunction<Double, Double, Double> getFunction() {
		return function;
	}
}
