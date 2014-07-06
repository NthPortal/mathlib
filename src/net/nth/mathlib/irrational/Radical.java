package net.nth.mathlib.irrational;

import java.util.ArrayList;

import net.nth.mathlib.fraction.Fraction;
import net.nth.mathlib.util.Algebra;

public class Radical
{
	private int radicand;
	private Fraction coefficient;
	private Fraction exponent;

	public Radical()
	{
	}

	public Radical(int whole)
	{
		this.radicand = 1;
		this.coefficient = new Fraction(whole);
		this.exponent = new Fraction(0);
	}

	public Radical(Fraction whole)
	{
		this.radicand = 1;
		this.coefficient = new Fraction(whole);
		this.exponent = new Fraction(0);
	}

	public Radical(int scalar, int radicand, Fraction power)
	{
		this.radicand = radicand;
		this.coefficient = new Fraction(scalar);
		this.exponent = new Fraction(power);
		this.reduce();
	}

	public Radical(Fraction scalar, int base, Fraction power)
	{
		this.radicand = base;
		this.coefficient = new Fraction(scalar);
		this.exponent = new Fraction(power);
		this.reduce();
	}

	public Radical(Radical i)
	{
		this.radicand = i.radicand;
		this.coefficient = new Fraction(i.coefficient);
		this.exponent = new Fraction(i.exponent);
	}

	private void reduce()
	{
		Fraction temp = new Fraction(this.radicand);

		if (this.exponent.isInt())
		{
			temp = temp.pow(this.exponent);
			this.coefficient = this.coefficient.multiply(temp);
			this.radicand = 1;
			this.exponent = new Fraction(0);
		}
		else
		{
			int numer = this.exponent.getNumer();
			temp = temp.pow(numer);
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
					this.coefficient = this.coefficient.multiply(current);

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
		result.coefficient = this.coefficient.add(r.coefficient);
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
		result.coefficient = this.coefficient.subtract(r.coefficient);
		return result;
	}

	public void print()
	{
		this.coefficient.print();
		System.out.print("(" + this.radicand + ")^(");
		this.exponent.print();
		System.out.print(")");
	}

	public void println()
	{
		this.coefficient.print();
		System.out.print("(" + this.radicand + ")^(");
		this.exponent.print();
		System.out.println(")");
	}

	public static void main(String[] args)
	{
		Radical test = new Radical(1, 32, new Fraction(1, 3));
		test.println();
	}
}
