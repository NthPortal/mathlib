package com.github.nthportal.mathlib.irrational;

import java.util.ArrayList;

import com.github.nthportal.mathlib.fraction.Fraction;
import com.github.nthportal.mathlib.util.Algebra;

public class Radical implements Comparable<Radical>
{
	private int radicand;
	private int coefficient;
	private Fraction exponent;

	private Radical()
	{
	}

	public Radical(int whole)
	{
		this.radicand = 1;
		this.coefficient = whole;
		this.exponent = new Fraction(0);
	}

	public Radical(int scalar, int radicand, Fraction power)
	{
		this.radicand = radicand;
		this.coefficient = scalar;
		this.exponent = new Fraction(power);
		this.reduce();
	}

	public Radical(Radical i)
	{
		this.radicand = i.radicand;
		this.coefficient = i.coefficient;
		this.exponent = new Fraction(i.exponent);
	}

	public int getRadicand()
	{
		return this.radicand;
	}

	public int getCoefficient()
	{
		return this.coefficient;
	}

	public Fraction getExponent()
	{
		return new Fraction(this.exponent);
	}

	public double toDouble()
	{
		return (this.coefficient * Math.pow(this.radicand,
				this.exponent.toDouble()));
	}

	private void reduce()
	{
		int temp = this.radicand;

		if (this.exponent.isInt())
		{
			temp = (int) Math.pow(temp, this.exponent.toInt());
			this.coefficient *= temp;
			this.radicand = 1;
			this.exponent = new Fraction(0);
		}
		else
		{
			int numer = this.exponent.getNumer();
			temp = (int) Math.pow(temp, numer);
			this.exponent = this.exponent.divide(numer);
			this.reduceBase();
			if (this.radicand == 1)
			{
				this.exponent = new Fraction(0);
			}
		}
	}

	// should only be called from reduce()
	private void reduceBase()
	{
		ArrayList<Integer> factors = Algebra.calcPrimeFactors(this.radicand);
		int root = this.exponent.getDenom();
		int size = factors.size();
		int current = factors.get(0);
		int counter = 1;
		int factor;

		for (int i = 1; i < size; i++)
		{
			factor = factors.get(i);
			if (factor == current)
			{
				counter++;
				if (counter == root)
				{
					for (int j = 0; j < root; j++)
					{
						this.radicand /= current;
					}
					this.coefficient *= current;

					i++;
					if (!(i == size))
					{
						current = factors.get(i);
						counter = 1;
					}
				}
			}
			else
			{
				current = factor;
				counter = 1;
			}
		}
	}

	public Radical add(Radical r)
	{
		if ((this.radicand != r.radicand)
				|| (this.exponent.compareTo(r.exponent) != 0))
		{
			throw new DifferentRadicalException(
					"Addition cannot be performed on numbers with different radicands or exponents.");
		}

		Radical result = new Radical(this);
		result.coefficient = this.coefficient + r.coefficient;
		return result;
	}

	public Radical subtract(Radical r)
	{
		if ((this.radicand != r.radicand)
				|| (this.exponent.compareTo(r.exponent) != 0))
		{
			throw new DifferentRadicalException(
					"Addition cannot be performed on numbers with different radicands or exponents.");
		}

		Radical result = new Radical(this);
		result.coefficient = this.coefficient + r.coefficient;
		return result;
	}

	public Radical multiply(int num)
	{
		Radical result = new Radical(this);
		result.coefficient *= num;
		return result;
	}

	public Radical multiply(Radical r)
	{
		Radical result = new Radical();
		int expDenom1 = this.exponent.getDenom();
		int expDenom2 = r.exponent.getDenom();
		int gcf = Algebra.gcf(expDenom1, expDenom2);

		result.coefficient = this.coefficient * r.coefficient;

		result.radicand = (int) Math.pow(this.radicand, (expDenom2 / gcf));
		result.radicand *= (int) Math.pow(r.radicand, (expDenom1 / gcf));

		result.exponent = new Fraction(1, (expDenom1 * expDenom2 / gcf));

		return result;
	}

	public Radical divide(int num)
	{
		if (!(this.coefficient % num == 0))
		{
			throw new NonWholeDivisionException();
		}

		Radical result = new Radical(this);
		result.coefficient /= num;
		return result;
	}

	public Radical pow(int exponent)
	{
		return new Radical(this.coefficient, this.radicand,
				(this.exponent.multiply(exponent)));
	}

	public Radical pow(Fraction exponent)
	{
		return new Radical(this.coefficient, this.radicand,
				(this.exponent.multiply(exponent)));
	}

	public int compareTo(double num)
	{
		double doubleThis = this.toDouble();

		if (doubleThis < num)
		{
			return -1;
		}
		else if (doubleThis == num)
		{
			return 0;
		}
		// Else
		return 1;
	}

	public int compareTo(Radical r)
	{
		double doubleThis = this.toDouble();
		double doubleR = r.toDouble();

		if (doubleThis < doubleR)
		{
			return -1;
		}
		else if (doubleThis == doubleR)
		{
			return 0;
		}
		// Else
		return 1;
	}

	public boolean equals(Radical r)
	{
		if ((this.radicand == r.radicand)
				&& (this.coefficient == r.coefficient)
				&& (this.exponent.equals(r.exponent)))
		{
			return true;
		}
		// Else
		return false;
	}

	public void print()
	{
		System.out.print(this.coefficient + "(" + this.radicand + ")^(");
		this.exponent.print();
		System.out.print(")");
	}

	public void println()
	{
		System.out.print(this.coefficient + "(" + this.radicand + ")^(");
		this.exponent.print();
		System.out.println(")");
	}

	public static void main(String[] args)
	{
		Radical test1 = new Radical(1, 32, new Fraction(1, 3));
		test1.println();
		Radical test2 = new Radical(1, 3, new Fraction(1, 2));
		test2.println();

		Radical result = test1.multiply(test2);
		result.println();
	}
}
