package net.nth.mathlib.matrix;

@SuppressWarnings("serial")
public class NonSquareMatrixException extends ArithmeticException
{
	public NonSquareMatrixException()
	{
		super("Invalid operation with a non-square matrix.");
	}
	
	public NonSquareMatrixException(String errMsg)
	{
		super(errMsg);
	}
}
