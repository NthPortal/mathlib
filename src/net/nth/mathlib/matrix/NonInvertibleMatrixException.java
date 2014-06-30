package net.nth.mathlib.matrix;

@SuppressWarnings("serial")
public class NonInvertibleMatrixException extends ArithmeticException
{
	public NonInvertibleMatrixException()
	{
		super("Matrix is not invertible.");
	}
	
	public NonInvertibleMatrixException(String errMsg)
	{
		super(errMsg);
	}
}
