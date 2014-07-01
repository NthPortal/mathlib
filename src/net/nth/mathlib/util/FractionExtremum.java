package net.nth.mathlib.util;

import net.nth.mathlib.fraction.Fraction;

public class FractionExtremum
{
	ExtremumType type;
	Fraction value;
	
	public FractionExtremum()
	{
	}

	public FractionExtremum(ExtremumType type, Fraction value)
	{
		this.type = type;
		this.value = value;
	}

	public ExtremumType getType()
	{
		return type;
	}

	public Fraction getValue()
	{
		return value;
	}
}
