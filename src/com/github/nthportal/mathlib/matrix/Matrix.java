package com.github.nthportal.mathlib.matrix;

public class Matrix extends MatrixType
{
	private boolean square;

	public Matrix(int rows, int cols)
	{
		super(rows, cols);
		this.square = testSquare();
	}

	public Matrix(int size)
	{
		super(size);
		this.square = true;
	}

	public Matrix(Matrix m)
	{
		super(m);
		this.square = m.square;
	}

	public boolean isSquare()
	{
		return this.square;
	}

	private boolean testSquare()
	{
		if (this.getNumRows() == this.getNumCols())
		{
			return true;
		}
		else
		{
			return false;
		}
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
			return identity(this.getNumRows());
		}

		Matrix result = new Matrix(this);

		for (int i = 0; i < (exp - 1); i++)
		{
			result = result.multiply(this);
		}

		return result;
	}

	public static Matrix identity(int size)
	{
		Matrix identity = new Matrix(size);

		for (int row = 0; row < identity.rows; row++)
		{
			for (int col = 0; col < identity.cols; col++)
			{
				if (row == col)
				{
					identity.matrix[row][col] = 1;
				}
				else
				{
					identity.matrix[row][col] = 0;
				}
			}
		}

		return identity;
	}

	public double det() throws NonSquareMatrixException
	{
		if (!this.square)
		{
			throw new NonSquareMatrixException(
					"Cannot compute the determinant of a non-square matrix.");
		}

		int numRowSwaps = 0;
		double det = 1;
		Matrix tempMatrix = new Matrix(this);

		numRowSwaps = tempMatrix.reduceForwardPhase();

		for (int i = 0; i < this.rows; i++)
		{
			det *= tempMatrix.matrix[i][i];
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

	public int reduceForwardPhase()
	{
		int numRowSwaps = 0;
		int smallerSize = 0;

		if (this.rows <= this.cols)
		{
			smallerSize = this.rows;
		}
		else
		{
			smallerSize = this.cols;
		}

		// "i < smallerSize - 1" (not "i < smallerSize")
		// because last row doesn't get reduced
		for (int i = 0; i < smallerSize - 1; i++)
		{
			if (this.matrix[i][i] == 0)
			{
				for (int j = i + 1; j < this.rows; j++)
				{
					if (this.matrix[j][i] != 0)
					{
						rowSwap(i, j);
						numRowSwaps++;
						i--;
						break;
					}
				}
			}
			else
			{
				for (int j = i + 1; j < this.rows; j++)
				{
					rowReplace(j, i, this.matrix[j][i] / this.matrix[i][i]);
				}
			}
			// this.print();
			// System.out.println();
		}
		return numRowSwaps;
	}

	// Should only be run after ReduceForwardPhase()
	public void reduceBackwardPhase()
	{
		int smallerSize = 0;

		if (this.rows <= this.cols)
		{
			smallerSize = this.rows;
		}
		else
		{
			smallerSize = this.cols;
		}

		// only while i > 0 (and not when i == 0)
		// because last row doesn't get reduced
		for (int i = smallerSize - 1; i > 0; i--)
		{
			if (!(this.matrix[i][i] == 0))
			{
				for (int j = i - 1; j >= 0; j--)
				{
					rowReplace(j, i, this.matrix[j][i] / this.matrix[i][i]);
				}
			}
			// this.print();
			// System.out.println();
		}
	}

	public void reducedEchelon()
	{
		reduceForwardPhase();
		reduceBackwardPhase();

		for (int row = 0; row < this.rows; row++)
		{
			rowScale(row, 1 / this.matrix[row][row]);
		}

		// print();

		// Move zero rows to bottom HERE
		for (int row = 0; row < this.rows; row++)
		{
			if (this.matrix[row][row] == 0)
			{
				for (int i = row; i < (row - 1); i++)
				{
					rowSwap(i, (i + 1));
				}
			}
		}
	}

	public Matrix inverse() throws NonSquareMatrixException,
			NonInvertibleMatrixException
	{
		if (!this.square)
		{
			throw new NonSquareMatrixException(
					"Inverse not defined for a non-square matrix.");
		}

		Matrix tempMatrix = new Matrix(this);
		Matrix result = new Matrix(this.rows, this.cols);

		tempMatrix.augmentIdentity();
		tempMatrix.reducedEchelon();

		Matrix lhs = new Matrix(this.rows, this.cols);
		deepCopy(tempMatrix.matrix, lhs.matrix, lhs.rows, lhs.cols);
		if (lhs.det() == 0)
		{
			throw new NonInvertibleMatrixException();
		}

		for (int row = 0; row < this.rows; row++)
		{
			for (int col = 0; col < this.cols; col++)
			{
				result.matrix[row][col] = tempMatrix.matrix[row][(col + this.cols)];
			}
		}

		return result;
	}

	public Matrix transpose()
	{
		Matrix result = new Matrix(this.cols, this.rows);

		for (int row = 0; row < this.rows; row++)
		{
			for (int col = 0; col < this.cols; col++)
			{
				result.matrix[col][row] = this.matrix[row][col];
			}
		}

		return result;
	}

	private void augmentIdentity()
	{
		Matrix tempMatrix = new Matrix(this);

		this.cols *= 2;
		this.testSquare();
		this.matrix = new double[this.rows][(this.cols)];

		deepCopy(tempMatrix.matrix, this.matrix, tempMatrix.rows,
				tempMatrix.cols);

		// Augments with Identity matrix
		Matrix id = identity(this.rows);
		deepCopy(id.matrix, this.matrix, id.rows, id.cols, 0, 0, 0, id.cols);
	}

	private void rowSwap(int row1, int row2)
	{
		double[] tempRow = new double[this.cols];

		for (int col = 0; col < this.cols; col++)
		{
			tempRow[col] = this.matrix[row1][col];
			this.matrix[row1][col] = this.matrix[row2][col];
			this.matrix[row2][col] = tempRow[col];
		}
	}

	private void rowScale(int row, double scalar)
	{
		for (int col = 0; col < this.cols; col++)
		{
			this.matrix[row][col] *= scalar;
		}
	}

	private void rowReplace(int targetRow, int modRow, double scalar)
	{
		for (int col = 0; col < this.cols; col++)
		{
			this.matrix[targetRow][col] -= (scalar * this.matrix[modRow][col]);
		}
	}
}
