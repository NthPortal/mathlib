package net.nth.mathlib.matrix;

@SuppressWarnings("serial")
public class IncompatMatrixSizesException extends ArithmeticException
{
	public IncompatMatrixSizesException()
	{
		super("The sizes of these two matrices are incompatible for this operation.");
	}
	
	public IncompatMatrixSizesException(String errMsg)
	{
		super(errMsg);
	}
}
