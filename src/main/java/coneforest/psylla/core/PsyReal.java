package coneforest.psylla.core;
import coneforest.psylla.*;
import java.math.BigDecimal;

/**
*	A representation of {@code real} object.
*/
@Type("real")
public class PsyReal
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
	public PsyRealNumeric psySub(final PsyRealNumeric oNumeric)
	{
		return new PsyReal(value-oNumeric.doubleValue());
	}

	@Override
	public PsyRealNumeric psyMul(final PsyRealNumeric oNumeric)
	{
		return new PsyReal(value*oNumeric.doubleValue());
	}

	@Override
	public PsyRealNumeric psyDiv(final PsyRealNumeric oNumeric)
	{
		return new PsyReal(value/oNumeric.doubleValue());
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
	public PsyInteger psyCmp(final PsyRealNumeric oNumeric)
	{
		final var result=Double.compare(value, oNumeric.doubleValue());
		return result<0? PsyInteger.MINUS_ONE:
			result>0? PsyInteger.ONE: PsyInteger.ZERO;
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
	public boolean equals(final Object object)
	{
		return object instanceof PsyReal
				&& psyEq((PsyReal)object).booleanValue();
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
