package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
*	The representation of {@code real}.
*/
@Type("real")
public final class PsyReal
	implements PsyRealNumeric
{
	/**
	*	The number zero.
	*/
	public static final PsyReal ZERO=new PsyReal(0.D);

	/**
	*	The number one.
	*/
	public static final PsyReal	ONE=new PsyReal(1.D);

	/**
	*	The number two.
	*/
	public static final PsyReal TWO=new PsyReal(2.D);

	/**
	*	The number minus one.
	*/
	public static final PsyReal MINUS_ONE=new PsyReal(-1.D);

	/**
	*	The number π.
	*/
	public static final PsyReal PI=new PsyReal(Math.PI);

	/**
	*	The number e.
	*/
	public static final PsyReal E=new PsyReal(Math.E);

	/**
	*	The largest positive finite value, (2−<sup>−52</sup>)·2<sup>1023</sup>.
	*/
	public static final PsyReal MAX_VALUE=new PsyReal(Double.MAX_VALUE);

	/**
	*	The smallest positive nonzero value, 2<sup>−1074</sup>.
	*/
	public static final PsyReal MIN_VALUE=new PsyReal(Double.MIN_VALUE);

	/**
	*	The Not-a-Number (NaN) value.
	*/
	public static final PsyReal NAN=new PsyReal(Double.NaN);

	private final double value;

	/**
	*	Creates a new {@code real} representing the specified {@code double} value.
	*
	*	@param value a {@code double} value.
	*/
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
	public boolean isZero()
	{
		return value==0.D;
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
	public PsyRational rationalValue()
		throws PsyUndefinedResultException
	{
		if(value==0.D||value==-0.D)
			return PsyInteger.ZERO;
		if(Double.isNaN(value)||Double.isInfinite(value))
			throw new PsyUndefinedResultException();

		final long bits=Double.doubleToLongBits(value);
		final long mantissa=bits&0x000FFFFFFFFFFFFFL;
		final int exponent=(int)((bits&0x7FF0000000000000L)>>52);

		if(exponent==0)
			return (PsyRational)PsyInteger
					.of((bits&0x8000000000000000L)==0L? mantissa: -mantissa)
					.psyDiv(new PsyBigInteger(BigInteger.ZERO.flipBit(-51-Double.MAX_EXPONENT)));

		final int pow=exponent-Double.MAX_EXPONENT;

		PsyRational retval=(PsyRational)PsyInteger.of(mantissa)
				.psyDiv(PsyInteger.of(0x10000000000000L))
				.psyAdd(PsyInteger.ONE);
		if((bits&0x8000000000000000L)!=0L)
			retval=retval.psyNeg();
		return (PsyRational)(pow>=0?
				retval.psyMul((1+pow<Double.SIZE)?
						PsyInteger.of(1L<<pow):
						new PsyBigInteger(BigInteger.ZERO.flipBit(pow))):
				retval.psyDiv((1-pow<Double.SIZE)?
						PsyInteger.of(1L<<-pow):
						new PsyBigInteger(BigInteger.ZERO.flipBit(-pow))));
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
	public int compareTo(final PsyRealNumeric oNumeric)
	{
		return Double.compare(value, oNumeric.doubleValue());
	}

	@Override
	public String toSyntaxString()
	{
		if(value==Double.NEGATIVE_INFINITY)
			return "-∞";
		if(value==Double.POSITIVE_INFINITY)
			return "∞";
		return String.valueOf(value);
	}

	@Override
	public int hashCode()
	{
		return Double.hashCode(value);
	}

	@Override
	public boolean equals(final Object obj)
	{
		return obj instanceof PsyReal oReal
				&& psyEq(oReal).booleanValue();
	}

	/**
	*	{@return the {@code real} initialized to the value represented by the specified {@link
	*	String} image}
	*
	*	@param image the string to be parsed.
	*/
	public static PsyReal parseLiteral(final String image)
	{
		return new PsyReal(switch(image)
			{
				case "∞"->Double.POSITIVE_INFINITY;
				case "-∞"->Double.NEGATIVE_INFINITY;
				default->Double.parseDouble(image);
			});
	}
}
