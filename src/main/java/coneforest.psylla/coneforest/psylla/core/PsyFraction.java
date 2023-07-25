package coneforest.psylla.core;

import coneforest.psylla.*;
import java.math.BigInteger;

/**
*	A representation of {@code fraction}.
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
		throws PsyUndefinedResultException
	{
		var x=numerator;
		var y=denominator;
		if(y==0L)
			throw new PsyUndefinedResultException();
		if(y<0L)
		{
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
	public PsyInteger psyCmp(final PsyRealNumeric oRealNumeric)
	{
		// TODO
		return null;
	}

	@Override
	public PsyReal psyRound()
	{
		return new PsyReal(Math.round((double)numerator/(double)denominator));
	}

	@Override
	public PsyRational psyAbs()
	{
		// TODO
		return new PsyFraction((numerator>=0L)? numerator: -numerator, denominator);
	}

	@Override
	public PsyRealNumeric psyMul(final PsyRealNumeric oRealNumeric)
	{
		try
		{
			if(oRealNumeric instanceof PsyIntegral oIntegral)
				return psyNumerator().psyMul(oIntegral).psyDiv(psyDenominator());
			if(oRealNumeric instanceof PsyRational oRational)
				return psyNumerator().psyMul(oRational.psyNumerator())
						.psyDiv(psyDenominator().psyMul(oRational.psyDenominator()));
			if(oRealNumeric instanceof PsyReal oReal)
				return new PsyReal(doubleValue()*oReal.doubleValue());
		}
		catch(final PsyUndefinedResultException e)
		{
			throw new AssertionError();
		}
		throw new ClassCastException();
	}

	@Override
	public PsyRealNumeric psyDiv(final PsyRealNumeric oRealNumeric)
		throws PsyUndefinedResultException
	{
		if(oRealNumeric instanceof PsyIntegral oIntegral)
			return psyNumerator().psyDiv(psyDenominator().psyMul(oIntegral));
		if(oRealNumeric instanceof PsyRational oRational)
			return psyNumerator().psyMul(oRational.psyDenominator())
					.psyDiv(psyDenominator().psyMul(oRational.psyNumerator()));
		if(oRealNumeric instanceof PsyReal oReal)
			return new PsyReal(doubleValue()*oReal.doubleValue());
		throw new ClassCastException();
	}

	@Override
	public PsyRealNumeric psyAdd(final PsyRealNumeric oRealNumeric)
	{
		try
		{
			if(oRealNumeric instanceof PsyIntegral oIntegral)
				return psyNumerator()
						.psyAdd(psyDenominator().psyMul(oIntegral))
						.psyDiv(psyDenominator());
			if(oRealNumeric instanceof PsyRational oRational)
				return psyNumerator().psyMul(oRational.psyDenominator())
						.psyAdd(psyDenominator().psyMul(oRational.psyNumerator()))
						.psyDiv(psyDenominator().psyMul(oRational.psyDenominator()));
			if(oRealNumeric instanceof PsyReal oReal)
				return new PsyReal(doubleValue()+oReal.doubleValue());
			throw new ClassCastException();
		}
		catch(final PsyUndefinedResultException e)
		{
			throw new AssertionError();
		}
	}

	@Override
	public PsyRealNumeric psySub(final PsyRealNumeric oRealNumeric)
	{
		try
		{
			if(oRealNumeric instanceof PsyIntegral)
				return psyNumerator()
						.psySub(psyDenominator().psyMul(oRealNumeric))
						.psyDiv(psyDenominator());
			if(oRealNumeric instanceof PsyRational oRational)
				return psyNumerator().psyMul(oRational.psyDenominator())
						.psySub(psyDenominator().psyMul(oRational.psyNumerator()))
						.psyDiv(psyDenominator().psyMul(oRational.psyDenominator()));
			if(oRealNumeric instanceof PsyReal oReal)
				return new PsyReal(doubleValue()-oReal.doubleValue());
			throw new ClassCastException();
		}
		catch(final PsyUndefinedResultException e)
		{
			throw new AssertionError();
		}
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
