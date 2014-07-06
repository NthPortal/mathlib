package net.nth.mathlib.irrational;

import java.util.ArrayList;

import net.nth.mathlib.fraction.Fraction;
import net.nth.mathlib.util.Algebra;

public class Irrational
{
	private int radicand;
	private Fraction coefficient;
	private Fraction exponent;

	public Irrational()
	{
	}

	public Irrational(int whole)
	{
		this.radicand = 1;
		this.coefficient = new Fraction(whole);
		this.exponent = new Fraction(0);
	}

	public Irrational(Fraction whole)
	{
		this.radicand = 1;
		this.coefficient = new Fraction(whole);
		this.exponent = new Fraction(0);
	}

	public Irrational(int scalar, int radicand, Fraction power)
	{
		this.radicand = radicand;
		this.coefficient = new Fraction(scalar);
		this.exponent = new Fraction(power);
		this.reduce();
	}

	public Irrational(Fraction scalar, int base, Fraction power)
	{
		this.radicand = base;
		this.coefficient = new Fraction(scalar);
		this.exponent = new Fraction(power);
		this.reduce();
	}

	public Irrational(Irrational i)
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
		Irrational test = new Irrational(1, 32, new Fraction(1, 3));
		test.println();
	}
}
