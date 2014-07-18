package com.github.nthportal.mathlib.polynomial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;

import com.github.nthportal.mathlib.fraction.Fraction;
import com.github.nthportal.mathlib.util.Algebra;
import com.github.nthportal.mathlib.util.ExtremumType;
import com.github.nthportal.mathlib.util.FractionExtremum;
import com.github.nthportal.mathlib.util.ZeroDivisionException;

public class FractionPolynomial
{
	private final List<FracPolyUnit> terms;
	private static final int SMALL_INTERVAL_MULTIPLIER = 1000;

	public FractionPolynomial()
	{
		this.terms = Collections
				.unmodifiableList(new ArrayList<FracPolyUnit>());
	}

	private FractionPolynomial(List<FracPolyUnit> list, boolean dummy)
	{
		this.terms = Collections.unmodifiableList(list);
	}

	public FractionPolynomial(ArrayList<FracPolyUnit> list)
	{
		List<FracPolyUnit> temp = new ArrayList<FracPolyUnit>(list);
		this.terms = Collections.unmodifiableList(temp);
	}

	// Creates a FractionPolynomial with one term
	public FractionPolynomial(Fraction coefficient, int exponent)
	{
		if (exponent < 0)
		{
			throw new InvalidExponentException();
		}

		List<FracPolyUnit> tempList = new ArrayList<FracPolyUnit>();
		tempList.add(new FracPolyUnit(coefficient, exponent));

		this.terms = Collections.unmodifiableList(tempList);
	}

	// Creates a FractionPolynomial with one term
	public FractionPolynomial(int coefficient, int exponent)
	{
		this(new Fraction(coefficient), exponent);
	}

	public FracPolyUnit getTerm(int index)
	{
		return terms.get(index);
	}

	public int getSize()
	{
		return this.terms.size();
	}

	public FractionPolynomial add(FracPolyUnit m)
	{
		// Returns original polynomial if monomial added has a 0 coefficient
		if (m.getCoefficient().compareTo(0) == 0)
		{
			return this;
		}

		List<FracPolyUnit> tempList = new ArrayList<FracPolyUnit>(this.terms);
		FracPolyUnit temp;

		for (ListIterator<FracPolyUnit> iterator = tempList.listIterator(); iterator
				.hasNext();)
		{
			FracPolyUnit next = iterator.next();
			if (m.getExponent() == next.getExponent())
			{
				temp = next.add(m);
				if (temp.getCoefficient().compareTo(0) == 0)
				{
					iterator.remove();
				}
				else
				{
					iterator.set(temp);
				}
				return new FractionPolynomial(tempList, true);
			}
		}
		// Else
		tempList.add(m);
		Collections.sort(tempList, Collections.reverseOrder());
		return new FractionPolynomial(tempList, true);
	}

	public FractionPolynomial add(FractionPolynomial p)
	{
		FractionPolynomial result = this;

		for (Iterator<FracPolyUnit> iterator = p.terms.iterator(); iterator
				.hasNext();)
		{
			result = result.add(iterator.next());
		}

		return result;
	}

	public FractionPolynomial subtract(FracPolyUnit m)
	{
		return this.add(m.multiply(-1));
	}

	public FractionPolynomial subtract(FractionPolynomial p)
	{
		FractionPolynomial result = this;

		for (Iterator<FracPolyUnit> iterator = p.terms.iterator(); iterator
				.hasNext();)
		{
			result = result.subtract(iterator.next());
		}

		return result;
	}

	public FractionPolynomial multiply(Fraction frac)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (Iterator<FracPolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result.add(iterator.next().multiply(frac));
		}

		return result;
	}

	public FractionPolynomial multiply(int num)
	{
		return this.multiply(new Fraction(num));
	}

	private FractionPolynomial multiply(FracPolyUnit m)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (Iterator<FracPolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result.add(iterator.next().multiply(m));
		}

		return result;
	}

	public FractionPolynomial multiply(FractionPolynomial p)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (Iterator<FracPolyUnit> iter = this.terms.iterator(); iter
				.hasNext();)
		{
			FracPolyUnit next = iter.next();
			for (Iterator<FracPolyUnit> jter = p.terms.iterator(); jter
					.hasNext();)
			{
				result = result.add(next.multiply(jter.next()));
			}
		}

		return result;
	}

	public FractionPolynomial divide(Fraction frac)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (Iterator<FracPolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result.add(iterator.next().divide(frac));
		}

		return result;
	}

	public FractionPolynomial divide(int scalar)
	{
		return this.divide(new Fraction(scalar));
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

		FractionPolynomial dividend = this;
		FracPolyUnit firstDividendTerm;
		FracPolyUnit firstDivisorTerm;
		FracPolyUnit temp;

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

		FractionPolynomial result = this;

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

	public Fraction eval(Fraction value)
	{
		Fraction result = new Fraction(0);

		for (Iterator<FracPolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result = result.add(iterator.next().eval(value));
		}

		return result;
	}

	public Fraction eval(int value)
	{
		return this.eval(new Fraction(value));
	}

	public FractionPolynomial derivative()
	{
		FractionPolynomial result = new FractionPolynomial();
		FracPolyUnit temp;

		for (Iterator<FracPolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			temp = iterator.next().derivative();
			if (temp.getCoefficient().compareTo(0) != 0)
			{
				result.add(temp);
			}
		}

		return result;
	}

	public FractionPolynomial antiDerivative()
	{
		FractionPolynomial result = new FractionPolynomial();

		for (Iterator<FracPolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result.add(iterator.next().antiDerivative());
		}

		return result;
	}

	public FractionPolynomial integral(Fraction constant)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (Iterator<FracPolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result.add(iterator.next().antiDerivative());
		}
		result.add(new FracPolyUnit(constant, 0));

		return result;
	}

	public FractionPolynomial integral(int constant)
	{
		return this.integral(new Fraction(constant));
	}

	public Fraction integral(Fraction lowerBound, Fraction upperBound)
	{
		Fraction result = new Fraction(0);

		for (Iterator<FracPolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			result = result.add(iterator.next()
					.integral(lowerBound, upperBound));
		}

		return result;
	}

	public Fraction integral(int lowerBound, int upperBound)
	{
		return this
				.integral(new Fraction(lowerBound), new Fraction(upperBound));
	}

	private FractionPolynomial makeIntCoefficients()
	{
		FractionPolynomial temp = this;
		int lcm = 1;

		for (Iterator<FracPolyUnit> iterator = temp.terms.iterator(); iterator
				.hasNext();)
		{
			lcm = Algebra.lcm(lcm, iterator.next().getCoefficient().getDenom());
		}
		temp = temp.multiply(lcm);

		return temp;
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
		FractionPolynomial func = this;
		ArrayList<Integer> numerFactors;
		ArrayList<Integer> denomFactors;
		Fraction possibleRoot;
		boolean success = true;

		// Loop until have all rational roots
		while (!func.terms.isEmpty() && (success))
		{
			func = func.makeIntCoefficients(); // CHECK IF NECESSARY

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

			loop: for (Iterator<Integer> numerIter = numerFactors.iterator(); numerIter
					.hasNext();)
			{
				Integer next = numerIter.next();

				for (Iterator<Integer> denomIter = denomFactors.iterator(); denomIter
						.hasNext();)
				{
					possibleRoot = new Fraction(next, denomIter.next());

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

		for (Iterator<Fraction> iter = possibleExtrema.iterator(); iter
				.hasNext();)
		{
			possibleExtremum = iter.next();
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

		for (Iterator<FracPolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			iterator.next().print();
			if (iterator.hasNext())
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
			System.out.print(0);
			return;
		}

		for (Iterator<FracPolyUnit> iterator = this.terms.iterator(); iterator
				.hasNext();)
		{
			iterator.next().print();
			if (iterator.hasNext())
			{
				System.out.print(" ");
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

		for (Iterator<FractionExtremum> iterator = extrema.iterator(); iterator
				.hasNext();)
		{
			iterator.next().print();

			if (iterator.hasNext())
			{
				System.out.print(", ");
			}
		}
		System.out.println();
	}
}