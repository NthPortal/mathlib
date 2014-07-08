package com.github.nthportal.mathlib.util;

@SuppressWarnings("serial")
public class SWRAIDKWException extends RuntimeException
{
	public SWRAIDKWException()
	{
		super("Something went wrong and I don't know what.");
	}
	
	public SWRAIDKWException(String errMsg)
	{
		super(errMsg);
	}
}
