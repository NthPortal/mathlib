package com.github.nthportal.mathlib.vector;

import com.github.nthportal.mathlib.matrix.MatrixType;

public class Vector extends MatrixType
{
	public Vector(int degree)
	{
		super(degree, 1);
	}

	public double innerProduct(Vector v)
	{
		if (!(this.rows == v.rows))
		{
			throw new IncompatVectorSizesException(
					"Cannot find the inner product (dot product) of vectors of different sizes.");
		}

		double result = 0;

		for (int i = 0; i < this.rows; i++)
		{
			result += (this.matrix[i][0] * v.matrix[i][0]);
		}

		return result;
	}

	public double dotProduct(Vector v)
	{
		return this.innerProduct(v);
	}
}
