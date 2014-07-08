package com.github.nthportal.mathlib.util;

import com.github.nthportal.mathlib.fraction.Fraction;

public class FractionExtremum
{
	ExtremumType type;
	Fraction value;

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
	
	public void print()
	{
		System.out.print("[");
		this.value.print();
		System.out.print(", " + this.type + "]");
	}
	
	public void println()
	{
		System.out.print("[");
		this.value.print();
		System.out.println(", " + this.type + "]");
	}
}
