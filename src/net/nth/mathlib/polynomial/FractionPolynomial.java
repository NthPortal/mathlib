package net.nth.mathlib.polynomial;

import java.util.ArrayList;

import net.nth.mathlib.fraction.Fraction;

public class FractionPolynomial
{
	public ArrayList<FractionMonomial> terms;

	public FractionPolynomial()
	{
	}

	public FractionPolynomial(ArrayList<FractionMonomial> monomials)
	{
		this.terms = monomials;
	}

	public FractionPolynomial(FractionPolynomial p)
	{
		this.terms = p.terms;
	}

	public FractionMonomial getTerm(int index)
	{
		return new FractionMonomial(terms.get(index));
	}

	public FractionPolynomial add(FractionMonomial m)
	{
		FractionPolynomial result = new FractionPolynomial(this);

		for (int i = 0; i < result.terms.size(); i++)
		{
			if (Fraction.compare(m.getExponent(), result.terms.get(i)
					.getExponent()))
			{
				result.terms.set(i,
						FractionMonomial.add(result.terms.get(i), m));
				return result;
			}
		}
		// Else
		result.terms.add(m);
		return result;
	}

	public static FractionPolynomial add(FractionPolynomial p1,
			FractionPolynomial p2)
	{
		FractionPolynomial result = new FractionPolynomial(p1);

		for (int i = 0; i < p2.terms.size(); i++)
		{
			result = result.add(p2.terms.get(i));
		}

		return result;
	}

	public FractionPolynomial subtract(FractionMonomial m)
	{
		FractionPolynomial result = new FractionPolynomial(this);

		for (int i = 0; i < result.terms.size(); i++)
		{
			if (Fraction.compare(m.getExponent(), result.terms.get(i)
					.getExponent()))
			{
				result.terms.set(i,
						FractionMonomial.subtract(result.terms.get(i), m));
				return result;
			}
		}
		// Else
		m = m.multiply(-1);
		result.terms.add(m);
		return result;
	}

	public static FractionPolynomial subtract(FractionPolynomial p1,
			FractionPolynomial p2)
	{
		FractionPolynomial result = new FractionPolynomial(p1);

		for (int i = 0; i < p2.terms.size(); i++)
		{
			result = result.subtract(p2.terms.get(i));
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
			if (!temp.getCoefficient().compare(0))
			{
				result.terms.add(temp);
			}
		}

		return result;
	}

	public FractionPolynomial integral()
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).integral());
		}

		return result;
	}

	public FractionPolynomial integral(Fraction constant)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).integral());
		}
		result.terms.add(new FractionMonomial(constant, new Fraction(0)));

		return result;
	}

	public Fraction integral(Fraction lowerBound, Fraction upperBound)
	{
		Fraction result = new Fraction(0);

		for (int i = 0; i < this.terms.size(); i++)
		{
			result = Fraction.add(result,
					this.terms.get(i).integral(lowerBound, upperBound));
		}

		return result;
	}
}