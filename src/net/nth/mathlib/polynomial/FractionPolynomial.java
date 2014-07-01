package net.nth.mathlib.polynomial;

import java.util.ArrayList;
import java.util.Collections;

import net.nth.mathlib.fraction.Fraction;

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

	public void reduce()
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

	public void order()
	{
		Collections.sort(this.terms);
	}

	public FractionPolynomial add(FractionMonomial m)
	{
		FractionPolynomial result = new FractionPolynomial(this);
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

	public FractionPolynomial divideBy(int scalar)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).divideBy(scalar));
		}

		return result;
	}

	public FractionPolynomial divideBy(Fraction frac)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).divideBy(frac));
		}

		return result;
	}

	public FractionPolynomial divideBy(FractionMonomial m)
	{
		FractionPolynomial result = new FractionPolynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).divideBy(m));
		}

		return result;
	}

	// Returns array of 2 FractionPolynomials: quotient then remainder
	public FractionPolynomial[] divideBy(FractionPolynomial p)
	{
		FractionPolynomial[] result = new FractionPolynomial[2];
		for (int i = 0; i < 2; i++)
		{
			result[i] = new FractionPolynomial();
		}
		FractionPolynomial temp = new FractionPolynomial();

		this.order();
		p.order();
		
		
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

		return roots;
	}
}