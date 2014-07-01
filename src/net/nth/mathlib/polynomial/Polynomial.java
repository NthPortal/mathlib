package net.nth.mathlib.polynomial;

import java.util.ArrayList;

import net.nth.mathlib.util.Extremum;

public class Polynomial
{
	public ArrayList<Monomial> terms;

	public Polynomial()
	{
		this.terms = new ArrayList<Monomial>();
	}

	public Polynomial(ArrayList<Monomial> monomials)
	{
		this.terms = monomials;
		this.reduce();
	}

	public Polynomial(Polynomial p)
	{
		this.terms = p.terms;
	}

	public Monomial getTerm(int index)
	{
		return new Monomial(terms.get(index));
	}

	public void reduce()
	{
		Polynomial temp = new Polynomial(this);
		Polynomial temp2 = new Polynomial();

		this.terms.clear();

		for (int i = 0; i < temp.terms.size(); i++)
		{
			temp2 = temp2.add(temp.terms.get(i));
		}

		this.terms = temp2.terms;
	}

	public Polynomial add(Monomial m)
	{
		Polynomial result = new Polynomial(this);
		Monomial temp;

		for (int i = 0; i < result.terms.size(); i++)
		{
			if (m.getExponent() == result.terms.get(i).getExponent())
			{
				temp = result.terms.get(i).add(m);
				if (temp.getCoefficient() == 0)
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

	public Polynomial add(Polynomial p)
	{
		Polynomial result = new Polynomial(this);

		for (int i = 0; i < p.terms.size(); i++)
		{
			result = result.add(p.terms.get(i));
		}

		return result;
	}

	public Polynomial subtract(Monomial m)
	{
		Polynomial result = new Polynomial(this);
		Monomial temp;

		for (int i = 0; i < result.terms.size(); i++)
		{
			if (m.getExponent() == result.terms.get(i).getExponent())
			{
				temp = result.terms.get(i).subtract(m);
				if (temp.getCoefficient() == 0)
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

	public Polynomial subtract(Polynomial p)
	{
		Polynomial result = new Polynomial(this);

		for (int i = 0; i < p.terms.size(); i++)
		{
			result = result.subtract(p.terms.get(i));
		}

		return result;
	}

	public Polynomial multiply(double scalar)
	{
		Polynomial result = new Polynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).multiply(scalar));
		}

		return result;
	}

	public Polynomial multiply(Monomial m)
	{
		Polynomial result = new Polynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).multiply(m));
		}

		return result;
	}

	public Polynomial multiply(Polynomial p)
	{
		Polynomial result = new Polynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			for (int j = 0; j < p.terms.size(); j++)
			{
				result = result.add(this.terms.get(i).multiply(p.terms.get(j)));
			}
		}

		return result;
	}

	public Polynomial divideBy(double scalar)
	{
		Polynomial result = new Polynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).divideBy(scalar));
		}

		return result;
	}

	public Polynomial divideBy(Monomial m)
	{
		Polynomial result = new Polynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).divideBy(m));
		}

		return result;
	}

	public double eval(double value)
	{
		double result = 0;

		for (int i = 0; i < this.terms.size(); i++)
		{
			result += this.terms.get(i).eval(value);
		}

		return result;
	}

	public Polynomial derivative()
	{
		Polynomial result = new Polynomial();
		Monomial temp;

		for (int i = 0; i < this.terms.size(); i++)
		{
			temp = this.terms.get(i).derivative();
			if (temp.getCoefficient() != 0)
			{
				result.terms.add(temp);
			}
		}

		return result;
	}

	public Polynomial antiDerivative()
	{
		Polynomial result = new Polynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).antiDerivative());
		}

		return result;
	}

	public Polynomial integral(double constant)
	{
		Polynomial result = new Polynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).antiDerivative());
		}
		result.terms.add(new Monomial(constant, 0));

		return result;
	}

	public double integral(double lowerBound, double upperBound)
	{
		double result = 0;

		for (int i = 0; i < this.terms.size(); i++)
		{
			result += this.terms.get(i).integral(lowerBound, upperBound);
		}

		return result;
	}

	public ArrayList<Extremum> calcExtrema()
	{
		ArrayList<Extremum> extrema = new ArrayList<Extremum>();

		return extrema;
	}
}