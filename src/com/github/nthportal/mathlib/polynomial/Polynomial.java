package com.github.nthportal.mathlib.polynomial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.github.nthportal.mathlib.util.ZeroDivisionException;

public class Polynomial
{
	private final List<PolyUnit> terms;

	public Polynomial()
	{
		this.terms = Collections.unmodifiableList(new ArrayList<PolyUnit>());
	}

	private Polynomial(List<PolyUnit> list)
	{
		this.terms = Collections.unmodifiableList(list);
	}

	// Creates a Polynomial with one term
	public Polynomial(double coefficient, int exponent)
	{
		if (exponent < 0)
		{
			throw new InvalidExponentException();
		}

		List<PolyUnit> tempList = new ArrayList<PolyUnit>();
		tempList.add(new PolyUnit(coefficient, exponent));

		this.terms = Collections.unmodifiableList(tempList);
	}

	public int getSize()
	{
		return this.terms.size();
	}

	public Polynomial add(PolyUnit m)
	{
		// Returns original polynomial if monomial added has a 0 coefficient
		if (m.getCoefficient() == 0)
		{
			return this;
		}

		List<PolyUnit> tempList = new ArrayList<PolyUnit>(this.terms);
		PolyUnit temp;

		for (ListIterator<PolyUnit> iterator = tempList.listIterator(); iterator
				.hasNext();)
		{
			PolyUnit next = iterator.next();
			if (m.getExponent() == next.getExponent())
			{
				temp = next.add(m);
				if (temp.getCoefficient() == 0)
				{
					iterator.remove();
				}
				else
				{
					iterator.set(temp);
				}
				return new Polynomial(tempList);
			}
		}
		// Else
		tempList.add(m);
		Collections.sort(tempList, Collections.reverseOrder());
		return new Polynomial(tempList);
	}

	public Polynomial add(Polynomial p)
	{
		Polynomial result = this;

		for (Iterator<PolyUnit> iterator = p.terms.iterator(); iterator
				.hasNext();)
		{
			result = result.add(iterator.next());
		}

		return result;
	}

	public Polynomial subtract(PolyUnit m)
	{
		return this.add(m.multiply(-1));
	}

	public Polynomial subtract(Polynomial p)
	{
		Polynomial result = this;

		for (Iterator<PolyUnit> iterator = p.terms.iterator(); iterator
				.hasNext();)
		{
			result = result.subtract(iterator.next());
		}

		return result;
	}

	public Polynomial multiply(double num)
	{
		Polynomial result = new Polynomial();

		for (Iterator<PolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result.add(iterator.next().multiply(num));
		}

		return result;
	}

	private Polynomial multiply(PolyUnit m)
	{
		Polynomial result = new Polynomial();

		for (Iterator<PolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result.add(iterator.next().multiply(m));
		}

		return result;
	}

	public Polynomial multiply(Polynomial p)
	{
		Polynomial result = new Polynomial();

		for (Iterator<PolyUnit> iter = this.terms.iterator(); iter.hasNext();)
		{
			PolyUnit next = iter.next();
			for (Iterator<PolyUnit> jter = p.terms.iterator(); jter.hasNext();)
			{
				result = result.add(next.multiply(jter.next()));
			}
		}

		return result;
	}

	public Polynomial divide(double num)
	{
		Polynomial result = new Polynomial();

		for (Iterator<PolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result.add(iterator.next().divide(num));
		}

		return result;
	}

	// Returns array of 2 FractionPolynomials: quotient then remainder
	public Polynomial[] divide(Polynomial divisor)
	{
		Polynomial[] result = new Polynomial[2];
		result[0] = new Polynomial();
		result[1] = new Polynomial();

		// Check for divisor or dividend being empty polynomial (equivalent to
		// 0)
		if (divisor.terms.isEmpty())
		{
			throw new ZeroDivisionException(
					"Cannot divide polynomial by a polynomial with value of 0.");
		}

		Polynomial dividend = this;
		PolyUnit firstDividendTerm;
		PolyUnit firstDivisorTerm;
		PolyUnit temp;

		while (true)
		{
			if (dividend.terms.isEmpty())
			{
				return result;
			}

			firstDividendTerm = dividend.terms.get(0);
			firstDivisorTerm = divisor.terms.get(0);

			// Check if dividing by higher order term
			if (firstDividendTerm.compareTo(firstDivisorTerm) == -1)
			{
				result[1] = dividend;
				return result;
			}

			temp = firstDividendTerm.divide(firstDivisorTerm);
			result[0].add(temp);

			dividend = dividend.subtract(divisor.multiply(temp));
		}
	}

	public Polynomial pow(int exp)
	{
		if (exp < 0)
		{
			throw new IllegalArgumentException(
					"Cannot raise polynomial to a negative power.");
		}

		if (exp == 0)
		{
			return new Polynomial(1, 0);
		}

		Polynomial result = this;

		for (int i = 0; i < (exp - 1); i++)
		{
			result = result.multiply(this);
		}

		return result;
	}

	public boolean equals(Polynomial p)
	{
		if (this.terms.size() != p.terms.size())
		{
			return false;
		}

		// Should work because polynomial should always be sorted
		Iterator<PolyUnit> otherIter = p.terms.iterator();
		for (Iterator<PolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			if (!iterator.next().equals(otherIter.next()))
			{
				return false;
			}
		}

		// Else
		return true;
	}

	public double eval(double value)
	{
		double result = 0;

		for (Iterator<PolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result += iterator.next().eval(value);
		}

		return result;
	}

	public Polynomial derivative()
	{
		Polynomial result = new Polynomial();
		PolyUnit temp;

		for (Iterator<PolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			temp = iterator.next().derivative();
			if (temp.getCoefficient() != 0)
			{
				result.add(temp);
			}
		}

		return result;
	}

	public Polynomial antiDerivative()
	{
		Polynomial result = new Polynomial();

		for (Iterator<PolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result.add(iterator.next().antiDerivative());
		}

		return result;
	}

	public Polynomial integral(double constant)
	{
		Polynomial result = new Polynomial();

		for (Iterator<PolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result.add(iterator.next().antiDerivative());
		}
		result.add(new PolyUnit(constant, 0));

		return result;
	}

	public double integral(double lowerBound, double upperBound)
	{
		double result = 0;

		for (Iterator<PolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result += iterator.next().integral(lowerBound, upperBound);
		}

		return result;
	}

	public void print()
	{
		int size = this.terms.size();

		for (int i = 0; i < size; i++)
		{
			this.terms.get(i).print();
			if (i != (size - 1))
			{
				System.out.print(" ");
			}
		}
	}

	public void println()
	{
		int size = this.terms.size();

		for (int i = 0; i < size; i++)
		{
			this.terms.get(i).print();
			if (i != (size - 1))
			{
				System.out.print(" ");
			}
		}

		System.out.println();
	}

	public static void main(String[] args)
	{
		Polynomial dividend = new Polynomial();
		Polynomial divisor = new Polynomial();

		dividend = dividend.add(new PolyUnit(4, 5));
		dividend = dividend.add(new PolyUnit(-3, 4));
		dividend = dividend.add(new PolyUnit(1, 3));
		dividend = dividend.add(new PolyUnit(-1, 1));
		dividend = dividend.add(new PolyUnit(6, 0));

		divisor = divisor.add(new PolyUnit(2, 2));
		divisor = divisor.add(new PolyUnit(-1, 1));
		divisor = divisor.add(new PolyUnit(3, 0));

		System.out.print("Dividend: ");
		dividend.println();

		System.out.print("Divisor: ");
		divisor.println();

		Polynomial[] result = dividend.divide(divisor);

		System.out.print("Quotient: ");
		result[0].println();

		System.out.print("Remainder: ");
		result[1].println();
	}
}