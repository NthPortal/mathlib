package net.nth.mathlib.number;

@SuppressWarnings("serial")
public class DifferentPowerException extends IllegalArgumentException
{
	public DifferentPowerException()
	{
		super("Operation cannot be performed on numbers with different exponents.");
	}
	
	public DifferentPowerException(String errMsg)
	{
		super(errMsg);
	}
}
