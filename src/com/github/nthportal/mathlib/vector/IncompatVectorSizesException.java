package com.github.nthportal.mathlib.vector;

@SuppressWarnings("serial")
public class IncompatVectorSizesException extends ArithmeticException
{
	public IncompatVectorSizesException()
	{
		super(
				"The sizes of these two vectors are incompatible for this operation.");
	}

	public IncompatVectorSizesException(String errMsg)
	{
		super(errMsg);
	}
}
