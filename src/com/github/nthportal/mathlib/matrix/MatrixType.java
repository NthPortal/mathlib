package com.github.nthportal.mathlib.matrix;


public class MatrixType
{
	protected double[][] matrix;
	protected int rows;
	protected int cols;

	protected MatrixType()
	{
		this.rows = 0;
		this.cols = 0;
		this.matrix = null;
	}

	public MatrixType(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		this.matrix = new double[rows][cols];
	}

	public MatrixType(int size)
	{
		this(size, size);
	}

	public MatrixType(MatrixType m)
	{
		this.rows = m.rows;
		this.cols = m.cols;
		this.matrix = new double[m.rows][m.cols];
		deepCopy(m.matrix, this.matrix, m.rows, m.cols);
	}

	protected static void deepCopy(double[][] source, double[][] target,
			int rows, int cols)
	{
		for (int row = 0; row < rows; row++)
		{
			for (int col = 0; col < cols; col++)
			{
				target[row][col] = source[row][col];
			}
		}
	}

	protected static void deepCopy(double[][] source, double[][] target,
			int rows, int cols, int sourceStartRow, int sourceStartCol,
			int targetStartRow, int targetStartCol)
	{
		for (int row = 0; row < rows; row++)
		{
			for (int col = 0; col < cols; col++)
			{
				target[row + targetStartRow][col + targetStartCol] = source[row
						+ sourceStartRow][col + sourceStartCol];
			}
		}
	}

	public int getNumRows()
	{
		return this.rows;
	}

	public int getNumCols()
	{
		return this.cols;
	}

	public double getSpot(int row, int col)
	{
		return this.matrix[row][col];
	}

	public void setSpot(int row, int col, double value)
	{
		this.matrix[row][col] = value;
	}

	public MatrixType add(MatrixType m) throws IncompatMatrixSizesException
	{
		if (!(this.rows == m.rows && this.cols == m.cols))
		{
			throw new IncompatMatrixSizesException(
					"Cannot add matrices of different dimensions.");
		}

		MatrixType result = new MatrixType(this.rows, this.cols);

		for (int row = 0; row < this.rows; row++)
		{
			for (int col = 0; col < this.cols; col++)
			{
				result.matrix[row][col] = this.matrix[row][col]
						+ m.matrix[row][col];
			}
		}

		return result;
	}

	public MatrixType subtract(MatrixType m)
			throws IncompatMatrixSizesException
	{
		if (!(this.rows == m.rows && this.cols == m.cols))
		{
			throw new IncompatMatrixSizesException(
					"Cannot subtract matrices of different dimensions.");
		}

		MatrixType result = new MatrixType(this.rows, this.cols);

		for (int row = 0; row < this.rows; row++)
		{
			for (int col = 0; col < this.cols; col++)
			{
				result.matrix[row][col] = this.matrix[row][col]
						- m.matrix[row][col];
			}
		}

		return result;
	}
	
	public Matrix multiply(Matrix m) throws IncompatMatrixSizesException
	{
		if (!(this.cols == m.rows))
		{
			throw new IncompatMatrixSizesException(
					"The first matrix must have the same number of columns as the second has rows.");
		}

		Matrix result = new Matrix(this.rows, m.cols);

		for (int row = 0; row < this.rows; row++)
		{
			for (int col = 0; col < m.cols; col++)
			{
				result.matrix[row][col] = 0;
				for (int m1Col = 0; m1Col < this.cols; m1Col++)
				{
					// m2Row = m1Col
					result.matrix[row][col] += this.matrix[row][m1Col]
							* m.matrix[m1Col][col];
				}
			}
		}

		return result;
	}

	public boolean equals(MatrixType m)
	{
		if (!(this.rows == m.rows && this.cols == m.cols))
		{
			return false;
		}

		for (int row = 0; row < this.rows; row++)
		{
			for (int col = 0; col < this.cols; col++)
			{
				if (!(this.matrix[row][col] == m.matrix[row][col]))
				{
					return false;
				}
			}
		}
		// Else
		return true;
	}

	public void print()
	{
		for (int i = 0; i < this.rows; i++)
		{
			for (int j = 0; j < this.cols; j++)
			{
				System.out.print(this.matrix[i][j]);
				if (!(j == this.cols - 1))
				{
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
}
