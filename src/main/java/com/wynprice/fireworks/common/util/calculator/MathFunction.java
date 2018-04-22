package com.wynprice.fireworks.common.util.calculator;

import java.util.function.Function;

public enum MathFunction implements Function<Double, Double>{
	SQRT(Math::sqrt),
	CBRT(Math::cbrt),
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
	ROUND(in -> (double)Math.round(in)),
	ABS(Math::abs),
	LOG(Math::log),
	LOG_TEN(Math::log10),
	LOG_ONE_P(Math::log1p),
	EXP(Math::exp),
	EXP_M_ONE(Math::expm1),
	EXPONANT(in -> (double)Math.getExponent(in)),
	RADIANS(Math::toRadians),
	DEGREES(Math::toDegrees)
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