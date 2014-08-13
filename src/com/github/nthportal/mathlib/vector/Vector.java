package com.github.nthportal.mathlib.vector;

import com.github.nthportal.mathlib.matrix.MatrixType;

public class Vector extends MatrixType
{
	private static final int CROSS_PRODUCT_ROWS = 3;

	private Vector(double[][] v)
	{
		super(v);

		if (!(this.cols == 1))
		{
			throw new IllegalArgumentException(
                    "Array must have exactly 1 col; this shouldn't ever be thrown.");
		}
	}

	public static Vector toVector(double[] v)
	{
		double[][] copy = new double[v.length][1];
		for (int row = 0; row < v.length; row++)
		{
			copy[row][0] = v[row];
		}
		return new Vector(copy);
	}

    protected Vector createInstance(double[][] matrix)
    {
        return new Vector(matrix);
    }

    public int getSize()
    {
        return this.rows;
    }

    public Vector add(Vector v)
    {
        if (!(this.rows == v.rows))
        {
            throw new IncompatVectorSizesException(
                    "Cannot add vectors of different dimensions.");
        }

        return new Vector(add(this.matrix, v.matrix, this.rows, this.cols));
    }

    public Vector subtract(Vector v)
    {
        if (!(this.rows == v.rows))
        {
            throw new IncompatVectorSizesException(
                    "Cannot subtract vectors of different dimensions.");
        }

        return new Vector(subtract(this.matrix, v.matrix, this.rows, this.cols));
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

	public double getSpot(int index)
	{
		return getSpot(index, 0);
	}

	public double dotProduct(Vector v)
	{
		return this.innerProduct(v);
	}

	public Vector crossProduct(Vector v)
	{
		if (!(this.rows == CROSS_PRODUCT_ROWS && this.rows == v.rows))
		{
			throw new IncompatVectorSizesException(
					"Cannot calculate the cross product of vectors with size other than 3.");
		}

		double[][] resultArray = new double[CROSS_PRODUCT_ROWS][1];

		resultArray[0][0] = ((this.matrix[1][0] * v.matrix[2][0]) - (this.matrix[2][0] * v.matrix[1][0]));
		resultArray[1][0] = ((this.matrix[2][0] * v.matrix[0][0]) - (this.matrix[0][0] * v.matrix[2][0]));
		resultArray[2][0] = ((this.matrix[0][0] * v.matrix[1][0]) - (this.matrix[1][0] * v.matrix[0][0]));

		return new Vector(resultArray);
	}
}
