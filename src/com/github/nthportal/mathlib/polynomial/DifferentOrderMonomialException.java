package com.github.nthportal.mathlib.polynomial;

@SuppressWarnings("serial")
public class DifferentOrderMonomialException extends IllegalArgumentException
{
	public DifferentOrderMonomialException()
	{
		super("Operation cannot be performed on monomials of different orders.");
	}
	
	public DifferentOrderMonomialException(String errMsg)
	{
		super(errMsg);
	}
}
