package com.github.nthportal.mathlib.matrix;

public abstract class MatrixType
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

    protected MatrixType(double[][] m, boolean dummy)
    {
        this.rows = m.length;
        if (this.rows == 0)
        {
            this.cols = 0;
        } else
        {
            this.cols = m[0].length;
        }

        this.matrix = m;
    }

    public MatrixType(double[][] m)
    {
        if (this.rows == 0)
        {
            this.cols = 0;
        } else
        {
            this.cols = m[0].length;
        }

        double[][] copy = new double[m.length][this.cols];
        deepCopy(m, copy, this.rows, this.cols);
        this.matrix = copy;
    }

    protected abstract <T extends MatrixType> T createInstance(double[][] matrix);

    protected static void deepCopy(double[][] source, double[][] target,
                                   int rows, int cols)
    {
        deepCopy(source, target, rows, cols, 0, 0, 0, 0);
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

    protected static double[][] add(double[][] m1, double[][] m2, int rows, int cols)
    {
        double[][] result = new double[rows][cols];

        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                result[row][col] = m1[row][col] + m2[row][col];
            }
        }

        return result;
    }

    protected static double[][] subtract(double[][] m1, double[][] m2, int rows, int cols)
    {
        double[][] result = new double[rows][cols];

        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                result[row][col] = m1[row][col]
                        - m2[row][col];
            }
        }

        return result;
    }

    public <T extends MatrixType> T multiply(T m) throws IncompatMatrixSizesException
    {
        if (!(this.cols == m.rows))
        {
            throw new IncompatMatrixSizesException(
                    "The first matrix must have the same number of columns as the second has rows.");
        }

        double[][] result = new double[this.rows][m.cols];

        for (int row = 0; row < this.rows; row++)
        {
            for (int col = 0; col < m.cols; col++)
            {
                result[row][col] = 0;
                for (int m1Col = 0; m1Col < this.cols; m1Col++)
                {
                    // m1Row = m2Col
                    result[row][col] += this.matrix[row][m1Col]
                            * m.matrix[m1Col][col];
                }
            }
        }

        return createInstance(result);
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
