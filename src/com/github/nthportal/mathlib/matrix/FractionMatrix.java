package com.github.nthportal.mathlib.matrix;

import java.util.Random;

import com.github.nthportal.mathlib.fraction.Fraction;
import com.github.nthportal.mathlib.util.SWWAIDKWException;

public class FractionMatrix
{
	private Fraction[][] matrix;
	private int rows;
	private int cols;
	private boolean square;

	public FractionMatrix()
	{
		this.rows = 0;
		this.cols = 0;
		this.square = false;
		this.matrix = null;
	}

	public FractionMatrix(int size)
	{
		this.rows = size;
		this.cols = size;
		this.square = true;
		this.matrix = new Fraction[size][size];
	}

	public FractionMatrix(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		this.testSquare();
		this.matrix = new Fraction[rows][cols];
	}

	public FractionMatrix(FractionMatrix m)
	{
		this.rows = m.rows;
		this.cols = m.cols;
		this.square = m.square;
		this.matrix = new Fraction[m.rows][m.cols];
		deepCopy(m.matrix, this.matrix, m.rows, m.cols);
	}

	private static void deepCopy(Fraction[][] source, Fraction[][] target,
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

	private static void deepCopy(Fraction[][] source, Fraction[][] target,
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

	public int getSize() throws NonSquareMatrixException
	{
		if (!this.square)
		{
			throw new NonSquareMatrixException(
					"The matrix is not square - GetSize() doesn't work.");
		}
		return this.rows;
	}

	public int getNumRows()
	{
		return this.rows;
	}

	public int getNumCols()
	{
		return this.cols;
	}

	public Fraction getSpot(int row, int col)
	{
		return new Fraction(this.matrix[row][col]);
	}

	public void setSpot(int row, int col, Fraction value)
	{
		this.matrix[row][col] = value;
	}

	public FractionMatrix add(FractionMatrix m)
			throws IncompatMatrixSizesException
	{
		if (!(this.rows == m.rows && this.cols == m.cols))
		{
			throw new IncompatMatrixSizesException(
					"Cannot add matrices of different dimensions.");
		}

		FractionMatrix result = new FractionMatrix(this.rows, this.cols);

		for (int row = 0; row < this.rows; row++)
		{
			for (int col = 0; col < this.cols; col++)
			{
				result.matrix[row][col] = this.matrix[row][col]
						.add(m.matrix[row][col]);
			}
		}

		return result;
	}

	public FractionMatrix subtract(FractionMatrix m)
			throws IncompatMatrixSizesException
	{
		if (!(this.rows == m.rows && this.cols == m.cols))
		{
			throw new IncompatMatrixSizesException(
					"Cannot subtract matrices of different dimensions.");
		}

		FractionMatrix result = new FractionMatrix(this.rows, this.cols);

		for (int row = 0; row < this.rows; row++)
		{
			for (int col = 0; col < this.cols; col++)
			{
				result.matrix[row][col] = this.matrix[row][col]
						.subtract(m.matrix[row][col]);
			}
		}

		return result;
	}

	public FractionMatrix multiply(FractionMatrix m)
			throws IncompatMatrixSizesException
	{
		if (!(this.cols == m.rows))
		{
			throw new IncompatMatrixSizesException(
					"The first matrix must have the same number of columns as the second has rows.");
		}

		FractionMatrix result = new FractionMatrix(this.rows, m.cols);

		for (int row = 0; row < this.rows; row++)
		{
			for (int col = 0; col < m.cols; col++)
			{
				result.matrix[row][col] = new Fraction(0);
				for (int m1Col = 0; m1Col < this.cols; m1Col++)
				{
					// m2Row = m1Col
					result.matrix[row][col] = result.matrix[row][col]
							.add(this.matrix[row][m1Col]
									.multiply(m.matrix[m1Col][col]));
				}
			}
		}

		return result;
	}

	public FractionMatrix pow(int exp)
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

		FractionMatrix result = new FractionMatrix(this);

		for (int i = 0; i < (exp - 1); i++)
		{
			result = result.multiply(this);
		}

		return result;
	}

	public static boolean compare(FractionMatrix m1, FractionMatrix m2)
	{
		if (!(m1.rows == m2.rows && m1.cols == m2.cols))
		{
			return false;
		}

		for (int row = 0; row < m1.rows; row++)
		{
			for (int col = 0; col < m1.cols; col++)
			{
				if (!(m1.matrix[row][col] == m2.matrix[row][col]))
				{
					return false;
				}
			}
		}

		// They have different values even though they have the same dimensions
		if (m1.square != m2.square)
		{
			throw new SWWAIDKWException();
		}

		// Else
		return true;
	}

	public boolean equals(FractionMatrix m)
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

		// They have different values even though they have the same dimensions
		if (this.square != m.square)
		{
			throw new SWWAIDKWException();
		}

		// Else
		return true;
	}

	public static FractionMatrix identity(int size)
	{
		FractionMatrix identity = new FractionMatrix(size);

		for (int row = 0; row < identity.rows; row++)
		{
			for (int col = 0; col < identity.cols; col++)
			{
				if (row == col)
				{
					identity.matrix[row][col] = new Fraction(1);
				}
				else
				{
					identity.matrix[row][col] = new Fraction(0);
				}
			}
		}

		return identity;
	}

	public Fraction det() throws NonSquareMatrixException
	{
		if (!this.square)
		{
			throw new NonSquareMatrixException(
					"Cannot compute the determinant of a non-square matrix.");
		}

		int numRowSwaps = 0;
		Fraction det = new Fraction(1);
		FractionMatrix tempMatrix = new FractionMatrix(this);

		numRowSwaps = tempMatrix.reduceForwardPhase();

		for (int i = 0; i < this.rows; i++)
		{
			det = det.multiply(tempMatrix.matrix[i][i]);
		}

		if (numRowSwaps % 2 == 1)
		{
			det.multiply(-1);
		}

		// Not sure if necessary in Java - was needed in C++
		if (det.compareTo(-0) == 0)
		{
			det = new Fraction(0);
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
			if (this.matrix[i][i].compareTo(0) == 0)
			{
				for (int j = i + 1; j < this.rows; j++)
				{
					if (this.matrix[j][i].compareTo(0) != 0)
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
					rowReplace(j, i,
							this.matrix[j][i].divide(this.matrix[i][i]));
				}
			}
			this.print();
			System.out.println();
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
			if (this.matrix[i][i].compareTo(0) != 0)
			{
				for (int j = i - 1; j >= 0; j--)
				{
					rowReplace(j, i,
							this.matrix[j][i].divide(this.matrix[i][i]));
				}
			}
			this.print();
			System.out.println();
		}
	}

	public void reducedEchelon()
	{
		reduceForwardPhase();
		reduceBackwardPhase();

		for (int row = 0; row < this.rows; row++)
		{
			rowScale(row, this.matrix[row][row].reciprocal());
		}

		// print();

		// Move zero rows to bottom HERE
		for (int row = 0; row < this.rows; row++)
		{
			if (this.matrix[row][row].compareTo(0) == 0)
			{
				for (int i = row; i < (row - 1); i++)
				{
					rowSwap(i, (i + 1));
				}
			}
		}
	}

	public FractionMatrix inverse() throws NonSquareMatrixException,
			NonInvertibleMatrixException
	{
		if (!this.square)
		{
			throw new NonSquareMatrixException(
					"Inverse not defined for a non-square matrix.");
		}

		FractionMatrix tempMatrix = new FractionMatrix(this);
		FractionMatrix result = new FractionMatrix(this.rows, this.cols);

		tempMatrix.augmentIdentity();
		tempMatrix.reducedEchelon();

		FractionMatrix lhs = new FractionMatrix(this.rows, this.cols);
		deepCopy(tempMatrix.matrix, lhs.matrix, lhs.rows, lhs.cols);
		if (lhs.det().compareTo(0) == 0)
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

	public FractionMatrix transpose()
	{
		FractionMatrix result = new FractionMatrix(this.cols, this.rows);

		for (int row = 0; row < this.rows; row++)
		{
			for (int col = 0; col < this.cols; col++)
			{
				result.matrix[col][row] = this.matrix[row][col];
			}
		}

		return result;
	}

	public void print()
	{
		for (int i = 0; i < this.rows; i++)
		{
			for (int j = 0; j < this.cols; j++)
			{
				this.matrix[i][j].print();
				if (!(j == this.cols - 1))
				{
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}

	private void testSquare()
	{
		if (this.rows == this.cols)
		{
			this.square = true;
		}
		else
		{
			this.square = false;
		}
	}

	private void augmentIdentity()
	{
		FractionMatrix tempMatrix = new FractionMatrix(this);

		this.cols *= 2;
		this.testSquare();
		this.matrix = new Fraction[this.rows][this.cols];

		deepCopy(tempMatrix.matrix, this.matrix, tempMatrix.rows,
				tempMatrix.cols);

		// Augments with Identity matrix
		FractionMatrix id = identity(this.rows);
		deepCopy(id.matrix, this.matrix, id.rows, id.cols, 0, 0, 0, id.cols);
	}

	private void rowSwap(int row1, int row2)
	{
		Fraction[] tempRow = new Fraction[this.cols];

		for (int col = 0; col < this.cols; col++)
		{
			tempRow[col] = this.matrix[row1][col];
			this.matrix[row1][col] = this.matrix[row2][col];
			this.matrix[row2][col] = tempRow[col];
		}
	}

	private void rowScale(int row, Fraction scalar)
	{
		for (int col = 0; col < this.cols; col++)
		{
			this.matrix[row][col] = this.matrix[row][col].multiply(scalar);
		}
	}

	private void rowReplace(int targetRow, int modRow, Fraction scalar)
	{
		for (int col = 0; col < this.cols; col++)
		{
			this.matrix[targetRow][col] = this.matrix[targetRow][col]
					.subtract(scalar.multiply(this.matrix[modRow][col]));
		}
	}

	public static void main(String[] args)
	{
		final int MAX = 9;
		final int MIN = 1;
		final int ROWS = 2;
		final int COLS = 2;
		final int NUM_MATRICES = 1;

		Random rand = new Random();

		FractionMatrix[] matrices = new FractionMatrix[NUM_MATRICES];
		for (int i = 0; i < NUM_MATRICES; i++)
		{
			matrices[i] = new FractionMatrix(ROWS, COLS);
			for (int row = 0; row < ROWS; row++)
			{
				for (int col = 0; col < COLS; col++)
				{
					matrices[i].matrix[row][col] = new Fraction(
							rand.nextInt(MAX - MIN) + MIN);
				}
			}
		}

		System.out.println("Original matrix:");
		matrices[0].print();
		System.out.println();

		FractionMatrix inverse = matrices[0].inverse();

		System.out.println("Inverse:");
		inverse.print();
		System.out.println();

		FractionMatrix result = matrices[0].multiply(inverse);

		System.out.println("Product:");
		result.print();
		System.out.println();
	}
}
