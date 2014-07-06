package net.nth.mathlib.irrational;

@SuppressWarnings("serial")
public class DifferentRadicalException extends IllegalArgumentException
{
	public DifferentRadicalException()
	{
		super(
				"Operation cannot be performed on numbers with different radicands or exponents.");
	}

	public DifferentRadicalException(String errMsg)
	{
		super(errMsg);
	}
}
