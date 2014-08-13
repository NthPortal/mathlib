package com.github.nthportal.mathlib.matrix;

public final class Matrix extends MatrixType
{
	private boolean square;

    private Matrix(double[][] m, boolean dummy)
    {
        super(m, dummy);
        this.square = testSquare();
    }

    public Matrix(double[][] m)
    {
        super(m);
        this.square = testSquare();
    }

    protected Matrix createInstance(double[][] matrix)
    {
        return new Matrix(matrix, true);
    }

	public boolean isSquare()
	{
		return this.square;
	}

	private boolean testSquare()
	{
		return (this.rows == this.cols);
	}

    public Matrix add(Matrix m)
    {
        if (!(this.rows == m.rows && this.cols == m.cols))
        {
            throw new IncompatMatrixSizesException(
                    "Cannot add matrices of different dimensions.");
        }

        return new Matrix(add(this.matrix, m.matrix, this.rows, this.cols), true);
    }

    public Matrix subtract(Matrix m)
    {
        if (!(this.rows == m.rows && this.cols == m.cols))
        {
            throw new IncompatMatrixSizesException(
                    "Cannot subtract matrices of different dimensions.");
        }

        return new Matrix(subtract(this.matrix, m.matrix, this.rows, this.cols), true);
    }

	public Matrix pow(int exp)
	{
		if (!this.square)
		{
			throw new NonSquareMatrixException(
					"Cannot raise a non-square matrix to a power.");
		}

		if (exp < 0)
		{
			throw new IllegalArgumentException(
					"Cannot raise matrix to a negative power.");
		}

		if (exp == 0)
		{
			return identity(this.rows);
		}

		Matrix result = this;

		for (int i = 0; i < (exp - 1); i++)
		{
			result = result.multiply(this);
		}

		return result;
	}

	public static Matrix identity(int size)
	{
		double[][] identity = new double[size][size];

		for (int row = 0; row < size; row++)
		{
			for (int col = 0; col < size; col++)
			{
				if (row == col)
				{
					identity[row][col] = 1;
				}
				else
				{
					identity[row][col] = 0;
				}
			}
		}

		return new Matrix(identity, true);
	}

    private static double det(double[][] matrix, int rows, int cols)
    {
        double det = 1;

        double[][] tempMatrix = new double[rows][cols];
        deepCopy(matrix, tempMatrix, rows, cols);

        int numRowSwaps = reduceForwardPhase(tempMatrix, rows, cols);

        for (int i = 0; i < rows; i++)
        {
            det *= tempMatrix[i][i];
        }

        if (numRowSwaps % 2 == 1)
        {
            det *= -1;
        }

        // Not sure if necessary in Java - was needed in C++
        if (det == -0)
        {
            det = 0;
        }

        return det;
    }

	public double det() throws NonSquareMatrixException
	{
		if (!this.square)
		{
			throw new NonSquareMatrixException(
					"Cannot compute the determinant of a non-square matrix.");
		}

        return det(this.matrix, this.rows, this.cols);
	}

	public static int reduceForwardPhase(double[][] matrix, int rows, int cols)
	{
		int numRowSwaps = 0;
		int smallerSize;

		if (rows <= cols)
		{
			smallerSize = rows;
		}
		else
		{
			smallerSize = cols;
		}

		// "i < smallerSize - 1" (not "i < smallerSize")
		// because last row doesn't get reduced
		for (int i = 0; i < smallerSize - 1; i++)
		{
			if (matrix[i][i] == 0)
			{
				for (int j = i + 1; j < rows; j++)
				{
					if (matrix[j][i] != 0)
					{
						rowSwap(matrix, rows, cols, i, j);
						numRowSwaps++;
						i--;
						break;
					}
				}
			}
			else
			{
				for (int j = i + 1; j < rows; j++)
				{
					rowReplace(matrix, cols, j, i, matrix[j][i] / matrix[i][i]);
				}
			}
			// this.print();
			// System.out.println();
		}

		return numRowSwaps;
	}

	// Should only be run after ReduceForwardPhase()
	public static void reduceBackwardPhase(double[][] matrix, int rows, int cols)
	{
		int smallerSize = 0;

		if (rows <= cols)
		{
			smallerSize = rows;
		}
		else
		{
			smallerSize = cols;
		}

		// only while i > 0 (and not when i == 0)
		// because last row doesn't get reduced
		for (int i = smallerSize - 1; i > 0; i--)
		{
			if (!(matrix[i][i] == 0))
			{
				for (int j = i - 1; j >= 0; j--)
				{
					rowReplace(matrix, cols, j, i, matrix[j][i] / matrix[i][i]);
				}
			}
			// this.print();
			// System.out.println();
		}
	}

	public Matrix reducedEchelon()
	{
        double[][] tempMatrix = new double[this.rows][this.cols];
        deepCopy(this.matrix, tempMatrix, this.rows, this.cols);

		reduceForwardPhase(tempMatrix, this.rows, this.cols);
		reduceBackwardPhase(tempMatrix, this.rows, this.cols);

		for (int row = 0; row < this.rows; row++)
		{
			rowScale(tempMatrix, cols, row, 1 / this.matrix[row][row]);
		}

		// Move zero rows to bottom HERE
		for (int row = 0; row < this.rows; row++)
		{
			if (this.matrix[row][row] == 0)
			{
				for (int i = row; i < (row - 1); i++)
				{
					rowSwap(tempMatrix, rows, cols, i, (i + 1));
				}
			}
		}

        return new Matrix(tempMatrix, true);
	}

	public Matrix inverse() throws NonSquareMatrixException,
			NonInvertibleMatrixException
	{
		if (!this.square)
		{
			throw new NonSquareMatrixException(
					"Inverse not defined for a non-square matrix.");
		}

		double[][] result = new double[this.rows][this.cols];

		Matrix tempMatrix = this.augmentIdentity();
		tempMatrix = tempMatrix.reducedEchelon();

		double[][] lhs = new double[this.rows][this.cols];
		deepCopy(tempMatrix.matrix, lhs, this.rows, this.cols);
		if (det(lhs, this.rows, this.cols) == 0)
		{
			throw new NonInvertibleMatrixException();
		}

		for (int row = 0; row < this.rows; row++)
		{
			for (int col = 0; col < this.cols; col++)
			{
				result[row][col] = tempMatrix.matrix[row][(col + this.cols)];
			}
		}

		return new Matrix(result, true);
	}

	public Matrix transpose()
	{
		double[][] result = new double[this.cols][this.rows];

		for (int row = 0; row < this.rows; row++)
		{
			for (int col = 0; col < this.cols; col++)
			{
				result[col][row] = this.matrix[row][col];
			}
		}

		return new Matrix(result, true);
	}

	private Matrix augmentIdentity()
	{
		double[][] tempMatrix = new double[this.rows][(this.cols * 2)];

		deepCopy(this.matrix, tempMatrix, this.rows, this.cols);

		// Augments with Identity matrix
		Matrix id = identity(this.rows);
		deepCopy(id.matrix, tempMatrix, id.rows, id.cols, 0, 0, 0, id.cols);

        return new Matrix(tempMatrix, true);
	}

	private static void rowSwap(double[][] matrix, int rows, int cols, int row1, int row2)
	{
		double[] tempRow = new double[cols];

		for (int col = 0; col < cols; col++)
		{
			tempRow[col] = matrix[row1][col];
			matrix[row1][col] = matrix[row2][col];
			matrix[row2][col] = tempRow[col];
		}
	}

	private static void rowScale(double[][] matrix, int cols, int row, double scalar)
	{
		for (int col = 0; col < cols; col++)
		{
			matrix[row][col] *= scalar;
		}
	}

	private static void rowReplace(double[][] matrix, int cols, int targetRow, int modRow, double scalar)
	{
		for (int col = 0; col < cols; col++)
		{
			matrix[targetRow][col] -= (scalar * matrix[modRow][col]);
		}
	}
}
