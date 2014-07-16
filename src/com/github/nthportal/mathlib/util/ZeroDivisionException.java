package com.github.nthportal.mathlib.util;

@SuppressWarnings("serial")
public class ZeroDivisionException extends ArithmeticException
{
	public ZeroDivisionException()
	{
		super("Cannot divide by 0.");
	}
	
	public ZeroDivisionException(String errMsg)
	{
		super(errMsg);
	}
}
