package net.nth.mathlib.polynomial;

import java.util.ArrayList;

public class Polynomial
{
	public ArrayList<Monomial> terms;

	public Polynomial()
	{
	}

	public Polynomial(ArrayList<Monomial> monomials)
	{
		this.terms = monomials;
	}

	public Polynomial(Polynomial p)
	{
		this.terms = p.terms;
	}

	public Monomial getTerm(int index)
	{
		return new Monomial(terms.get(index));
	}

	public void add(Monomial m)
	{
		for (int i = 0; i < this.terms.size(); i++)
		{
			if (m.getExponent() == this.terms.get(i).getExponent())
			{
				this.terms.set(i, Monomial.add(this.terms.get(i), m));
				return;
			}
		}
		// Else
		this.terms.add(m);
	}

	public static Polynomial add(Polynomial p1, Polynomial p2)
	{
		Polynomial result = new Polynomial(p1);

		for (int i = 0; i < p2.terms.size(); i++)
		{
			result.add(p2.terms.get(i));
		}

		return result;
	}

	public void subtract(Monomial m)
	{
		for (int i = 0; i < this.terms.size(); i++)
		{
			if (m.getExponent() == this.terms.get(i).getExponent())
			{
				this.terms.set(i, Monomial.subtract(this.terms.get(i), m));
				return;
			}
		}
		// Else
		m = m.multiply(-1);
		this.terms.add(m);
	}
	
	public static Polynomial subtract(Polynomial p1, Polynomial p2)
	{
		Polynomial result = new Polynomial(p1);

		for (int i = 0; i < p2.terms.size(); i++)
		{
			result.subtract(p2.terms.get(i));
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

	public Polynomial integral()
	{
		Polynomial result = new Polynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).integral());
		}

		return result;
	}

	public Polynomial integral(double constant)
	{
		Polynomial result = new Polynomial();

		for (int i = 0; i < this.terms.size(); i++)
		{
			result.terms.add(this.terms.get(i).integral());
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
}