package net.nth.mathlib.util;

public class Extremum
{
	ExtremumType type;
	double value;

	public Extremum()
	{
	}

	public Extremum(ExtremumType type, double value)
	{
		this.type = type;
		this.value = value;
	}

	public ExtremumType getType()
	{
		return type;
	}

	public double getValue()
	{
		return value;
	}
}
