package com.github.nthportal.mathlib.irrational;

@SuppressWarnings("serial")
public class NonWholeDivisionException extends IllegalArgumentException
{
	public NonWholeDivisionException()
	{
		super("Operation gives Radical a fractional coefficient.");
	}

	public NonWholeDivisionException(String errMsg)
	{
		super(errMsg);
	}
}
