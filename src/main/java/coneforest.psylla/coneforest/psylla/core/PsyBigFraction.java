package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.math.BigInteger;

/**
*	The representation of {@code bigfraction}.
*/
@Type("bigfraction")
public final class PsyBigFraction
	implements PsyRational
{
	private final BigInteger numerator, denominator;

	private PsyBigFraction(final BigInteger numerator, final BigInteger denominator)
	{
		this.numerator=numerator;
		this.denominator=denominator;
	}

	/**
	*	TODO
	*
	*	@param numerator the numerator.
	*	@param denominator the denominator.
	*/
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
				final var t=x;
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
		return y.equals(BigInteger.ONE)? PsyIntegral.of(x): new PsyBigFraction(x, y);
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
	public boolean isZero()
	{
		return false;
	}

	@Override
	public PsyIntegral psyToIntegral()
	{
		return psyRound();
	}

	@Override
	public PsyIntegral psyRound()
	{
		final var intpart=numerator.divide(denominator);
		return PsyIntegral.of(numerator.subtract(
				intpart.multiply(denominator)).shiftLeft(1).compareTo(denominator)<0?
						intpart: intpart.add(BigInteger.ONE));
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
}
