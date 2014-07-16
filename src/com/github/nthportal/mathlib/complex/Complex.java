package com.github.nthportal.mathlib.complex;

import com.github.nthportal.mathlib.util.SWWAIDKWException;

public class Complex
{
	private double real;
	private double imaginary;

	private Complex()
	{
	}

	public Complex(double realCoef, double imaginaryCoef)
	{
		this.real = realCoef;
		this.imaginary = imaginaryCoef;
	}

	public Complex(double imagNum)
	{
		this.real = 0;
		this.imaginary = imagNum;
	}

	public Complex(Complex c)
	{
		this.real = c.real;
		this.imaginary = c.imaginary;
	}

	public Complex add(Complex c)
	{
		return new Complex((this.real + c.real), (this.imaginary + c.imaginary));
	}

	public Complex subtract(Complex c)
	{
		return new Complex((this.real - c.real), (this.imaginary - c.imaginary));
	}

	public Complex multiply(double scalar)
	{
		return new Complex((this.real * scalar), (this.imaginary * scalar));
	}

	public Complex multiply(Complex c)
	{
		Complex result = new Complex();
		result.real = ((this.real * c.real) - (this.imaginary * c.imaginary));
		result.imaginary = ((this.real * c.imaginary) + (this.imaginary * c.real));
		return result;
	}

	public Complex divide(double scalar)
	{
		return new Complex((this.real / scalar), (this.imaginary / scalar));
	}

	public Complex divide(Complex denom)
	{
		Complex numer = new Complex(this);

		Complex conjugate = new Complex(denom.real, (denom.imaginary * -1));

		numer = numer.multiply(conjugate);
		denom = denom.multiply(conjugate);

		if (!(denom.imaginary == 0))
		{
			throw new SWWAIDKWException();
		}

		return new Complex((numer.real / denom.real),
				(numer.imaginary / denom.real));
	}

	public boolean equals(Complex c)
	{
		if (this.real == c.real && this.imaginary == c.imaginary)
		{
			return true;
		}
		// Else
		return false;
	}

	public void print()
	{
		System.out.print(this.real + " + " + this.imaginary + "i");
	}

	public void println()
	{
		System.out.println(this.real + " + " + this.imaginary + "i");
	}
}
