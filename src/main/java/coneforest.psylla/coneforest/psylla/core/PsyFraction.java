package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.math.BigInteger;

/**
*	The representation of {@code fraction}.
*/
@Type("fraction")
public final class PsyFraction
	implements
		PsyRational
{
	private PsyFraction(final long numerator, final long denominator)
	{
		this.numerator=numerator;
		this.denominator=denominator;
	}

	public static PsyRational of(final long numerator, final long denominator)
	{
		var x=numerator;
		var y=denominator;
		if(y==0L)
			throw new IllegalArgumentException();
		if(y==Long.MIN_VALUE || x==Long.MIN_VALUE)
			return PsyRational.of(PsyInteger.of(x).psyNeg(), PsyInteger.of(y).psyNeg());
		if(y<0L)
		{
			// TODO x=-x
			x=-x;
			y=-y;
		}
		if(x<0L)
			x=-x;
		while(x!=0L)
		{
			if(x>y)
			{
				var t=x;
				x=y;
				y=t;
				continue;
			}
			y%=x;
		}
		x=numerator/y;
		y=denominator/y;
		if(y<0L)
		{
			x=-x;
			y=-y;
		}
		return (y!=1L)? new PsyFraction(x, y): PsyInteger.of(x);
	}

	@Override
	public double doubleValue()
	{
		return ((double)numerator)/((double)denominator);
	}

	@Override
	public BigInteger bigIntegerValue()
	{
		return BigInteger.valueOf(longValue());
	}

	@Override
	public long longValue()
	{
		return Math.floorDiv(numerator, denominator);
	}

	@Override
	public int intValue()
	{
		return (int)longValue();
	}

	@Override
	public PsyInteger psyNumerator()
	{
		return PsyInteger.of(numerator);
	}

	@Override
	public PsyInteger psyDenominator()
	{
		return PsyInteger.of(denominator);
	}

	@Override
	public PsyInteger psyFloor()
	{
		return PsyInteger.of(numerator<0? numerator/denominator-1: numerator/denominator);
	}

	@Override
	public PsyInteger psyCeiling()
	{
		return PsyInteger.of(numerator<0? numerator/denominator: numerator/denominator+1);
	}

	@Override
	public PsyBoolean psyIsZero()
	{
		return PsyBoolean.FALSE;
	}

	@Override
	public PsyIntegral psyToIntegral()
	{
		// TODO
		return null;
	}

	@Override
	public PsyReal psyRound()
	{
		return new PsyReal(Math.round(doubleValue()));
	}

	@Override
	public PsyInteger psySignum()
	{
		return PsyInteger.of(Long.signum(numerator));
	}

	@Override
	public String toSyntaxString()
	{
		return String.format("%d:%d", numerator, denominator);
	}

	private final long numerator, denominator;
}
