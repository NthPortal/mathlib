package net.nth.mathlib.polynomial;

import java.util.ArrayList;
import java.util.Collections;

import net.nth.mathlib.fraction.Fraction;
import net.nth.mathlib.util.FractionExtremum;

public class FractionPolynomial
{
	public ArrayList<FractionMonomial> terms;

	public FractionPolynomial()
	{
		this.terms = new ArrayList<FractionMonomial>();
	}

	public FractionPolynomial(ArrayList<FractionMonomial> monomials)
	{
		this.terms = monomials;
		this.reduce();
	}

	public FractionPolynomial(FractionPolynomial p)
	{
		this.terms = p.terms;
	}

	public FractionMonomial getTerm(int index)
	{
		return new FractionMonomial(terms.get(index));
	}

	public int getSize()
	{
		return this.terms.size();
	}

	private void reduce()
	{
		FractionPolynomial temp = new FractionPolynomial(this);
		FractionPolynomial temp2 = new FractionPolynomial();

		this.terms.clear();

		for (int i = 0; i < temp.terms.size(); i++)
		{
			temp2 = temp2.add(temp.terms.get(i));
		}

		this.terms = temp2.terms;
	}

	private void order()
	{
		Collections.sort(this.terms, Collections.reverseOrder());
	}

	public FractionPolynomial add(FractionMonomial m)
	{
		FractionPolynomial result = new FractionPolynomial(this);

		// Returns original polynomial if monomial added has a 0 coefficient
		if (m.getCoefficient().compare(0) == 0)
		{
			return result;
		}

		FractionMonomial temp;

		for (int i = 0; i < result.terms.size(); i++)
		{
			if (m.getExponent().compareTo(result.terms.get(i).getExponent()) == 0)
			{
				temp = result.terms.get(i).add(m);
				if (temp.getCoefficient().compare(0) == 0)
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
		result.terms.add(m);
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

	public FractionPolynomial subtract(FractionMonomial m)
	{
		FractionPolynomial result = new FractionPolynomial(this);

		// Returns original polynomial if monomial subtracted has a 0
		// coefficient
		if (m.getCoefficient().compare(0) == 0)
		{
			return result;
		}

		FractionMonomial temp;

		for (int i = 0; i < result.terms.size(); i++)
		{
			if (m.getExponent().compareTo(result.terms.get(i).getExponent()) == 0)
			{
				temp = result.terms.get(i).subtract(m);
				if (temp.getCoefficient().compare(0) == 0)
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

	public FractionPolynomial multiply(FractionMonomial m)
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

	public FractionPolynomial divide(FractionMonomial m)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).divide(m));
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
		if (divisor.terms.size() == 0)
		{
			throw new ZeroDivisionException(
					"Cannot divide polynomial by a polynomial with value of 0.");
		}
		else if (this.terms.size() == 0)
		{
			return result;
		}

		FractionPolynomial dividend = new FractionPolynomial(this);
		FractionMonomial firstDividendTerm;
		FractionMonomial firstDivisorTerm;
		FractionMonomial temp = new FractionMonomial();

		while (true)
		{
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
		FractionMonomial temp;

		for (int i = 0; i < this.terms.size(); i++)
		{
			temp = this.terms.get(i).derivative();
			if (temp.getCoefficient().compare(0) != 0)
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
		result.terms.add(new FractionMonomial(new Fraction(constant),
				new Fraction(0)));

		return result;
	}

	public FractionPolynomial integral(Fraction constant)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).antiDerivative());
		}
		result.terms.add(new FractionMonomial(constant, new Fraction(0)));

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

	public ArrayList<Fraction> calcRoots()
	{
		ArrayList<Fraction> roots = new ArrayList<Fraction>();

		int notDone; // METHOD NOT DONE!!

		return roots;
	}

	public ArrayList<FractionExtremum> calcExtrema()
	{
		ArrayList<FractionExtremum> extrema = new ArrayList<FractionExtremum>();

		int notDone; // METHOD NOT DONE!!

		return extrema;
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
				System.out.print(" + ");
			}
		}

		System.out.println();
	}

	public static void main(String[] args)
	{
		FractionPolynomial dividend = new FractionPolynomial();
		FractionPolynomial divisor = new FractionPolynomial();

		dividend = dividend.add(new FractionMonomial(4, 5));
		dividend = dividend.add(new FractionMonomial(-3, 4));
		dividend = dividend.add(new FractionMonomial(1, 3));
		dividend = dividend.add(new FractionMonomial(-1, 1));
		dividend = dividend.add(new FractionMonomial(6, 0));

		divisor = divisor.add(new FractionMonomial(2, 2));
		divisor = divisor.add(new FractionMonomial(-1, 1));
		divisor = divisor.add(new FractionMonomial(3, 0));

		System.out.print("Dividend: ");
		dividend.println();

		System.out.print("Divisor: ");
		divisor.println();

		FractionPolynomial[] result = dividend.divide(divisor);

		System.out.print("Quotient: ");
		result[0].println();

		System.out.print("Remainder: ");
		result[1].println();
	}
}