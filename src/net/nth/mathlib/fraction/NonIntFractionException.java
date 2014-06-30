package net.nth.mathlib.fraction;

@SuppressWarnings("serial")
public class NonIntFractionException extends IllegalArgumentException
{
	public NonIntFractionException()
	{
		super("This operation not allowed for a fraction with a non-'1' denominator.");
	}
	
	public NonIntFractionException(String errMsg)
	{
		super(errMsg);
	}
}
