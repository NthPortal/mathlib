package com.github.nthportal.mathlib.polynomial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

import com.github.nthportal.mathlib.fraction.Fraction;
import com.github.nthportal.mathlib.util.Algebra;
import com.github.nthportal.mathlib.util.ExtremumType;
import com.github.nthportal.mathlib.util.FractionExtremum;
import com.github.nthportal.mathlib.util.ZeroDivisionException;

public class FractionPolynomial
{
	private ArrayList<FracPolyUnit> terms;
	private static final int SMALL_INTERVAL_MULTIPLIER = 1000;

	public FractionPolynomial()
	{
		this.terms = new ArrayList<FracPolyUnit>();
	}

	public FractionPolynomial(ArrayList<FracPolyUnit> monomials)
	{
		this.terms = new ArrayList<FracPolyUnit>();
		for (int i = 0; i < monomials.size(); i++)
		{
			this.add(new FracPolyUnit(monomials.get(i)));
		}
	}

	public FractionPolynomial(FractionPolynomial p)
	{
		this.terms = new ArrayList<FracPolyUnit>();
		for (int i = 0; i < p.terms.size(); i++)
		{
			this.terms.add(new FracPolyUnit(p.terms.get(i)));
		}
	}

	// Creates a FractionPolynomial with one term
	public FractionPolynomial(int coefficient, int exponent)
	{
		if (exponent < 0)
		{
			throw new InvalidExponentException();
		}

		this.terms = new ArrayList<FracPolyUnit>();
		this.terms.add(new FracPolyUnit(coefficient, exponent));
	}

	// Creates a FractionPolynomial with one term
	public FractionPolynomial(Fraction coefficient, int exponent)
	{
		if (exponent < 0)
		{
			throw new InvalidExponentException();
		}

		this.terms = new ArrayList<FracPolyUnit>();
		this.terms.add(new FracPolyUnit(coefficient, exponent));
	}

	public FracPolyUnit getTerm(int index)
	{
		return new FracPolyUnit(terms.get(index));
	}

	public int getSize()
	{
		return this.terms.size();
	}

	/*
	 * private void reduce() { FractionPolynomial temp = new
	 * FractionPolynomial(this); FractionPolynomial temp2 = new
	 * FractionPolynomial();
	 * 
	 * this.terms.clear();
	 * 
	 * for (int i = 0; i < temp.terms.size(); i++) { temp2 =
	 * temp2.add(temp.terms.get(i)); }
	 * 
	 * this.terms = temp2.terms; }
	 */

	private void order()
	{
		Collections.sort(this.terms, Collections.reverseOrder());
	}

	public FractionPolynomial add(FracPolyUnit m)
	{
		FractionPolynomial result = new FractionPolynomial(this);

		// Returns original polynomial if monomial added has a 0 coefficient
		if (m.getCoefficient().compareTo(0) == 0)
		{
			return result;
		}

		FracPolyUnit temp;

		for (int i = 0; i < result.terms.size(); i++)
		{
			if (m.getExponent() == result.terms.get(i).getExponent())
			{
				temp = result.terms.get(i).add(m);
				if (temp.getCoefficient().compareTo(0) == 0)
				{
					result.terms.remove(i);
				}
				else
				{
					result.terms.set(i, temp);
				}
				return result;
			}
		}
		// Else
		result.terms.add(new FracPolyUnit(m));
		result.order();
		return result;
	}

	public FractionPolynomial add(FractionPolynomial p)
	{
		FractionPolynomial result = new FractionPolynomial(this);

		for (int i = 0; i < p.terms.size(); i++)
		{
			result = result.add(p.terms.get(i));
		}

		return result;
	}

	public FractionPolynomial subtract(FracPolyUnit m)
	{
		FractionPolynomial result = new FractionPolynomial(this);

		// Returns original polynomial if monomial subtracted has a 0
		// coefficient
		if (m.getCoefficient().compareTo(0) == 0)
		{
			return result;
		}

		FracPolyUnit temp;

		for (int i = 0; i < result.terms.size(); i++)
		{
			if (m.getExponent() == result.terms.get(i).getExponent())
			{
				temp = result.terms.get(i).subtract(m);
				if (temp.getCoefficient().compareTo(0) == 0)
				{
					result.terms.remove(i);
				}
				else
				{
					result.terms.set(i, temp);
				}
				return result;
			}
		}
		// Else
		m = m.multiply(-1);
		result.terms.add(m);
		result.order();
		return result;
	}

	public FractionPolynomial subtract(FractionPolynomial p)
	{
		FractionPolynomial result = new FractionPolynomial(this);

		for (int i = 0; i < p.terms.size(); i++)
		{
			result = result.subtract(p.terms.get(i));
		}

		return result;
	}

	public FractionPolynomial multiply(int scalar)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).multiply(scalar));
		}

		return result;
	}

	public FractionPolynomial multiply(Fraction frac)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).multiply(frac));
		}

		return result;
	}

	private FractionPolynomial multiply(FracPolyUnit m)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).multiply(m));
		}

		return result;
	}

	public FractionPolynomial multiply(FractionPolynomial p)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			for (int j = 0; j < p.terms.size(); j++)
			{
				result = result.add(this.terms.get(i).multiply(p.terms.get(j)));
			}
		}

		return result;
	}

	public FractionPolynomial divide(int scalar)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).divide(scalar));
		}

		return result;
	}

	public FractionPolynomial divide(Fraction frac)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).divide(frac));
		}

		return result;
	}

	// Returns array of 2 FractionPolynomials: quotient then remainder
	public FractionPolynomial[] divide(FractionPolynomial divisor)
	{
		FractionPolynomial[] result = new FractionPolynomial[2];
		result[0] = new FractionPolynomial();
		result[1] = new FractionPolynomial();

		// Check for divisor or dividend being empty polynomial (equivalent to
		// 0)
		if (divisor.terms.isEmpty())
		{
			throw new ZeroDivisionException(
					"Cannot divide polynomial by a polynomial with value of 0.");
		}

		FractionPolynomial dividend = new FractionPolynomial(this);
		FracPolyUnit firstDividendTerm;
		FracPolyUnit firstDivisorTerm;
		FracPolyUnit temp = new FracPolyUnit();

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
				result[1] = new FractionPolynomial(dividend);
				return result;
			}

			temp = firstDividendTerm.divide(firstDivisorTerm);
			result[0].terms.add(temp);

			dividend = dividend.subtract(divisor.multiply(temp));
		}
	}

	public FractionPolynomial pow(int exp)
	{
		if (exp < 0)
		{
			throw new IllegalArgumentException(
					"Cannot raise polynomial to a negative power.");
		}

		if (exp == 0)
		{
			return new FractionPolynomial(1, 0);
		}

		FractionPolynomial result = new FractionPolynomial(this);

		for (int i = 0; i < (exp - 1); i++)
		{
			result = result.multiply(this);
		}

		return result;
	}

	public boolean equals(FractionPolynomial p)
	{
		int size = this.terms.size();

		if (size != p.terms.size())
		{
			return false;
		}

		// Should work because polynomial should always be sorted
		for (int i = 0; i < size; i++)
		{
			if (!this.terms.get(i).equals(p.terms.get(i)))
			{
				return false;
			}
		}

		// Else
		return true;
	}

	public Fraction eval(int value)
	{
		Fraction result = new Fraction(0);

		for (int i = 0; i < this.terms.size(); i++)
		{
			result = result.add(this.terms.get(i).eval(value));
		}

		return result;
	}

	public Fraction eval(Fraction value)
	{
		Fraction result = new Fraction(0);

		for (int i = 0; i < this.terms.size(); i++)
		{
			result = result.add(this.terms.get(i).eval(value));
		}

		return result;
	}

	public FractionPolynomial derivative()
	{
		FractionPolynomial result = new FractionPolynomial();
		FracPolyUnit temp;

		for (int i = 0; i < this.terms.size(); i++)
		{
			temp = this.terms.get(i).derivative();
			if (temp.getCoefficient().compareTo(0) != 0)
			{
				result.terms.add(temp);
			}
		}

		return result;
	}

	public FractionPolynomial antiDerivative()
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).antiDerivative());
		}

		return result;
	}

	public FractionPolynomial integral(int constant)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).antiDerivative());
		}
		result.terms.add(new FracPolyUnit(new Fraction(constant), 0));

		return result;
	}

	public FractionPolynomial integral(Fraction constant)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).antiDerivative());
		}
		result.terms.add(new FracPolyUnit(constant, 0));

		return result;
	}

	public Fraction integral(int lowerBound, int upperBound)
	{
		Fraction result = new Fraction(0);

		for (int i = 0; i < this.terms.size(); i++)
		{
			result = result.add(this.terms.get(i).integral(lowerBound,
					upperBound));
		}

		return result;
	}

	public Fraction integral(Fraction lowerBound, Fraction upperBound)
	{
		Fraction result = new Fraction(0);

		for (int i = 0; i < this.terms.size(); i++)
		{
			result = result.add(this.terms.get(i).integral(lowerBound,
					upperBound));
		}

		return result;
	}

	private void makeIntCoefficients()
	{
		FractionPolynomial temp = new FractionPolynomial(this);
		int lcm = 1;

		for (int i = 0; i < temp.terms.size(); i++)
		{
			lcm = Algebra.lcm(lcm, temp.terms.get(i).getCoefficient()
					.getDenom());
		}
		temp = temp.multiply(lcm);

		this.terms = temp.terms;
	}

	private static FractionPolynomial makeFactor(Fraction constant)
	{
		FractionPolynomial factor = new FractionPolynomial();

		factor = factor.add(new FracPolyUnit(1, 1));
		factor = factor.add(new FracPolyUnit(constant.multiply(-1), 0));

		return factor;
	}

	// Only finds rational roots
	public ArrayList<Fraction> calcRootsRational()
	{
		// Declare variables
		ArrayList<Fraction> roots = new ArrayList<Fraction>();
		FractionPolynomial func = new FractionPolynomial(this);
		ArrayList<Integer> numerFactors;
		ArrayList<Integer> denomFactors;
		Fraction possibleRoot;
		boolean success = true;

		// Loop until have all rational roots
		while ((func.terms.size() != 0) && (success))
		{
			func.makeIntCoefficients(); // CHECK IF NECESSARY

			if (func.terms.get(func.terms.size() - 1).getExponent() != 0)
			{
				numerFactors = new ArrayList<Integer>();
				numerFactors.add(0);
			}
			else
			{
				numerFactors = Algebra.calcFactors(func.terms
						.get(func.terms.size() - 1).getCoefficient().toInt());
			}
			denomFactors = Algebra.calcFactors(func.terms.get(0)
					.getCoefficient().toInt());

			success = false;

			loop: for (int i = 0; i < numerFactors.size(); i++)
			{
				for (int j = 0; j < denomFactors.size(); j++)
				{
					possibleRoot = new Fraction(numerFactors.get(i),
							denomFactors.get(j));

					for (int neg = 0; neg <= 1; neg++)
					{
						if (neg == 1)
						{
							possibleRoot = possibleRoot.multiply(-1);
						}

						if (func.eval(possibleRoot).compareTo(0) == 0)
						{
							roots.add(possibleRoot);
							func = func.divide(makeFactor(possibleRoot))[0];
							success = true;
							break loop;
						}
					}
				}
			}
		}
		return roots;
	}

	public ArrayList<FractionExtremum> calcExtrema()
	{
		ArrayList<FractionExtremum> extrema = new ArrayList<FractionExtremum>();
		Fraction secondDerivEval;
		Fraction possibleExtremum;

		FractionPolynomial firstDeriv = this.derivative();
		FractionPolynomial secondDeriv = firstDeriv.derivative();

		TreeSet<Fraction> possibleExtrema = new TreeSet<Fraction>(
				firstDeriv.calcRootsRational());

		Iterator<Fraction> extremaIter = possibleExtrema.iterator();
		while (extremaIter.hasNext())
		{
			possibleExtremum = new Fraction(extremaIter.next());
			secondDerivEval = secondDeriv.eval(possibleExtremum);

			// Second derivative test
			if (secondDerivEval.compareTo(0) == -1)
			{
				extrema.add(new FractionExtremum(ExtremumType.MAX,
						possibleExtremum));
			}
			else if (secondDerivEval.compareTo(0) == 1)
			{
				extrema.add(new FractionExtremum(ExtremumType.MIN,
						possibleExtremum));
			}
			// Else first derivative test
			else
			{
				Fraction smallInterval = new Fraction(1,
						possibleExtremum.getDenom() * SMALL_INTERVAL_MULTIPLIER);
				if ((firstDeriv.eval(possibleExtremum.subtract(smallInterval))
						.compareTo(0) == -1)
						&& (firstDeriv
								.eval(possibleExtremum.add(smallInterval))
								.compareTo(0) == 1))
				{
					extrema.add(new FractionExtremum(ExtremumType.MIN,
							possibleExtremum));
				}
				else if ((firstDeriv.eval(
						possibleExtremum.subtract(smallInterval)).compareTo(0) == 1)
						&& (firstDeriv
								.eval(possibleExtremum.add(smallInterval))
								.compareTo(0) == -1))
				{
					extrema.add(new FractionExtremum(ExtremumType.MAX,
							possibleExtremum));
				}
			}
		}

		return extrema;
	}

	public void print()
	{
		int size = this.terms.size();

		if (size == 0)
		{
			System.out.print(0);
			return;
		}

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

		if (size == 0)
		{
			System.out.println(0);
			return;
		}

		for (int i = 0; i < size; i++)
		{
			this.terms.get(i).print();
			if (i != (size - 1))
			{
				System.out.print(" + ");
			}
		}

		System.out.println();
	}

	public static void main(String[] args)
	{
		FractionPolynomial factorTest = new FractionPolynomial(1, 4);
		ArrayList<FractionExtremum> extrema;

		factorTest.println();

		extrema = factorTest.calcExtrema();

		int size = extrema.size();
		for (int i = 0; i < size; i++)
		{
			extrema.get(i).print();

			if (i != (size - 1))
			{
				System.out.print(", ");
			}
		}
		System.out.println();
	}
}