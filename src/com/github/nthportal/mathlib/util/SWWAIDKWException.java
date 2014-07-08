package com.github.nthportal.mathlib.util;

/*
 * SomethingWentWrongAndIDon'tKnowWhat Exception
 * This exception is thrown when some set of conditions
 *   which shouldn't have been possible to create occurs.
 */
@SuppressWarnings("serial")
public class SWWAIDKWException extends RuntimeException
{
	public SWWAIDKWException()
	{
		super("Something went wrong and I don't know what.");
	}

	public SWWAIDKWException(String errMsg)
	{
		super(errMsg);
	}
}
