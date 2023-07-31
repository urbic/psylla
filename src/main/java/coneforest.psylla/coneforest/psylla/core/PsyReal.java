package coneforest.psylla.core;

import coneforest.psylla.*;
import java.math.BigDecimal;

/**
*	The representation of {@code real}.
*/
@Type("real")
public final class PsyReal
	implements PsyRealNumeric
{
	public PsyReal(final double value)
	{
		this.value=value;
	}

	@Override
	public PsyIntegral psyToIntegral()
		throws PsyRangeCheckException
	{
		if(Double.isInfinite(value))
			throw new PsyRangeCheckException();
		return PsyIntegral.of(BigDecimal.valueOf(value).toBigInteger());
	}

	@Override
	public PsyBoolean psyIsZero()
	{
		return PsyBoolean.of(value==0.D);
	}

	@Override
	public int intValue()
	{
		return (int)value;
	}

	@Override
	public long longValue()
	{
		return (long)value;
	}

	@Override
	public double doubleValue()
	{
		return value;
	}

	@Override
	public PsyReal psyNeg()
	{
		return new PsyReal(-value);
	}

	@Override
	public PsyReal psyAbs()
	{
		return new PsyReal(Math.abs(value));
	}

	@Override
	public PsyReal psySignum()
	{
		return new PsyReal(Math.signum(value));
	}

	@Override
	public PsyReal psyAdd(final PsyRealNumeric oNumeric)
	{
		return new PsyReal(value+oNumeric.doubleValue());
	}

	@Override
	public PsyReal psySub(final PsyRealNumeric oNumeric)
	{
		return new PsyReal(value-oNumeric.doubleValue());
	}

	@Override
	public PsyReal psyReciprocal()
	{
		return new PsyReal(1.D/value);
	}

	@Override
	public PsyReal psyMul(final PsyRealNumeric oRealNumeric)
	{
		return new PsyReal(value*oRealNumeric.doubleValue());
	}

	/*
	@Override
	public PsyFloatingPoint psyDiv(final PsyNumeric oNumeric)
	{
		if(oNumeric instanceof PsyRealNumeric oRealNumeric)
			return new PsyReal(doubleValue()/oRealNumeric.doubleValue());
		else
		{
			final var x=oNumeric.realValue();
			final var y=oNumeric.imagValue();
			final var m=value/Math.hypot(x, y);
			return PsyFloatingPoint.of(x*m, -y*m);
		}
	}
	*/

	@Override
	public PsyReal psyDiv(final PsyRealNumeric oRealNumeric)
	{
		return new PsyReal(value/oRealNumeric.doubleValue());
	}

	@Override
	public PsyInteger psyRound()
	{
		return PsyInteger.of(Math.round(value));
	}

	@Override
	public PsyReal psyFloor()
	{
		return new PsyReal(Math.floor(value));
	}

	@Override
	public PsyReal psyCeiling()
	{
		return new PsyReal(Math.ceil(value));
	}

	@Override
	public int cmp(final PsyRealNumeric oNumeric)
	{
		return Double.compare(value, oNumeric.doubleValue());
	}


	@Override
	public String toSyntaxString()
	{
		return String.valueOf(value);
	}

	@Override
	public int hashCode()
	{
		return (int)value;
	}

	@Override
	public boolean equals(final Object obj)
	{
		return obj instanceof PsyReal oReal
				&& psyEq(oReal).booleanValue();
	}

	public static PsyReal parseLiteral(final String image)
	{
		return new PsyReal(Double.parseDouble(image));
	}

	public static final PsyReal
		ZERO=new PsyReal(0.D),
		ONE=new PsyReal(1.D),
		TWO=new PsyReal(2.D),
		MINUS_ONE=new PsyReal(-1.D),
		PI=new PsyReal(Math.PI),
		E=new PsyReal(Math.E),
		MAX_VALUE=new PsyReal(Double.MAX_VALUE),
		MIN_VALUE=new PsyReal(Double.MIN_VALUE),
		NAN=new PsyReal(Double.NaN);

	private final double value;
}
