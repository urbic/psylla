package coneforest.psylla.core;

import coneforest.psylla.*;
import java.math.BigInteger;

/**
*	The representation of {@code bigfraction}.
*/
@Type("bigfraction")
public final class PsyBigFraction
	implements PsyRational
{
	private PsyBigFraction(final BigInteger numerator, final BigInteger denominator)
	{
		this.numerator=numerator;
		this.denominator=denominator;
	}

	public static PsyRational of(final BigInteger numerator, final BigInteger denominator)
	{
		var x=numerator;
		var y=denominator;
		if(y.equals(BigInteger.ZERO))
			throw new IllegalArgumentException();
		if(y.compareTo(BigInteger.ZERO)<0)
		{
			x=x.negate();
			y=y.negate();
		}
		if(x.compareTo(BigInteger.ZERO)<0)
			x=x.negate();
		while(x.compareTo(BigInteger.ZERO)!=0)
		{
			if(x.compareTo(y)>0)
			{
				var t=x;
				x=y;
				y=t;
				continue;
			}
			y=y.mod(x);
		}
		x=numerator.divide(y);
		y=denominator.divide(y);
		if(y.compareTo(BigInteger.ZERO)<0)
		{
			x=x.negate();
			y=y.negate();
		}
		return (!y.equals(BigInteger.ONE))? new PsyBigFraction(x, y): PsyIntegral.of(x);
		//return (!y.equals(BigInteger.ONE))? new PsyRational.of(x, y): PsyIntegral.of(x);
	}

	@Override
	public double doubleValue()
	{
		// TODO ?
		return numerator.doubleValue()/denominator.doubleValue();
	}

	@Override
	public long longValue()
	{
		return (long)doubleValue();
	}

	@Override
	public BigInteger bigIntegerValue()
	{
		return psyFloor().bigIntegerValue();
	}

	@Override
	public PsyIntegral psyNumerator()
	{
		return PsyIntegral.of(numerator);
	}

	@Override
	public PsyIntegral psyDenominator()
	{
		return PsyIntegral.of(denominator);
	}

	@Override
	public PsyIntegral psyFloor()
	{
		return PsyIntegral.of(numerator.signum()<0?
				numerator.divide(denominator).subtract(BigInteger.ONE):
				numerator.divide(denominator));
	}

	@Override
	public PsyIntegral psyCeiling()
	{
		return PsyIntegral.of(numerator.signum()<0?
				numerator.divide(denominator):
				numerator.divide(denominator).add(BigInteger.ONE));
	}

	@Override
	public int intValue()
	{
		return (int)doubleValue();
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
		System.err.println("PsyBigFraction::psyToIntegral");
		return null;
	}

	@Override
	public PsyReal psyRound()
	{
		// TODO
		System.err.println("PsyBigFraction::psyRound");
		return null;
	}

	@Override
	public PsyInteger psySignum()
	{
		return PsyInteger.of(numerator.signum());
	}

	@Override
	public String toSyntaxString()
	{
		return String.format("%d:%d", numerator, denominator);
	}

	private final BigInteger numerator, denominator;
}
