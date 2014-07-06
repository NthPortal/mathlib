package com.github.nthportal.mathlib.polynomial;

@SuppressWarnings("serial")
public class InvalidExponentException extends IllegalArgumentException
{
	public InvalidExponentException()
	{
		super("Cannot have a monomial with a negative exponent.");
	}
	
	public InvalidExponentException(String errMsg)
	{
		super(errMsg);
	}
}
