package com.github.nthportal.mathlib.fraction;

@SuppressWarnings("serial")
public class ZeroDenomException extends ArithmeticException
{
	public ZeroDenomException()
	{
		super("Fraction cannot have a '0' denominator.");
	}
	
	public ZeroDenomException(String errMsg)
	{
		super(errMsg);
	}
}
