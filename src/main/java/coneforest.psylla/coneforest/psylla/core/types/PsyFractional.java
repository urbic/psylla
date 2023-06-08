package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyUndefinedResult;
import java.math.BigInteger;

@Type("fractional")
public class PsyFractional
	implements
		PsyRational
{
	private PsyFractional(final long numerator, final long denominator)
	{
		this.numerator=numerator;
		this.denominator=denominator;
	}

	public static PsyRational of(final long numerator, final long denominator)
		throws PsyUndefinedResult
	{
		var x=numerator;
		var y=denominator;
		if(y==0L)
			throw new PsyUndefinedResult();
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
		return (y!=1L)? new PsyFractional(x, y): PsyInteger.of(x);
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
		return new PsyFractional((numerator>=0L)? numerator: -numerator, denominator);
	}

	@Override
	public PsyRealNumeric psyMul(final PsyRealNumeric oRealNumeric)
	{
		try
		{
			if(oRealNumeric instanceof PsyIntegral)
				return psyNumerator().psyMul(oRealNumeric).psyDiv(psyDenominator());
			if(oRealNumeric instanceof PsyRational oRational)
				return psyNumerator().psyMul(oRational.psyNumerator())
						.psyDiv(psyDenominator().psyMul(oRational.psyDenominator()));
		}
		catch(final PsyUndefinedResult e)
		{
			// NOP
		}
		return new PsyReal(doubleValue()*oRealNumeric.doubleValue());
	}

	@Override
	public PsyRealNumeric psyDiv(final PsyRealNumeric oRealNumeric)
		throws PsyUndefinedResult
	{
		if(oRealNumeric instanceof PsyIntegral)
			return psyNumerator().psyDiv(psyDenominator().psyMul(oRealNumeric));
		if(oRealNumeric instanceof PsyRational oRational)
			return psyNumerator().psyMul(oRational.psyDenominator())
					.psyDiv(psyDenominator().psyMul(oRational.psyNumerator()));
		return new PsyReal(doubleValue()*oRealNumeric.doubleValue());
	}

	@Override
	public PsyRealNumeric psyAdd(final PsyRealNumeric oRealNumeric)
	{
		try
		{
			if(oRealNumeric instanceof PsyIntegral)
				return psyNumerator()
						.psyAdd(psyDenominator().psyMul(oRealNumeric))
						.psyDiv(psyDenominator());
			if(oRealNumeric instanceof PsyRational oRational)
				return psyNumerator().psyMul(oRational.psyDenominator())
						.psyAdd(psyDenominator().psyMul(oRational.psyNumerator()))
						.psyDiv(psyDenominator().psyMul(oRational.psyDenominator()));
		}
		catch(final PsyUndefinedResult e)
		{
			// NOP
		}
		return new PsyReal(doubleValue()+oRealNumeric.doubleValue());
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
		}
		catch(final PsyUndefinedResult e)
		{
			// NOP
		}
		return new PsyReal(doubleValue()+oRealNumeric.doubleValue());
	}

	@Override
	public PsyRational psyNeg()
	{
		try
		{
			return (PsyRational)psyNumerator().psyNeg().psyDiv(psyDenominator());
		}
		catch(final PsyUndefinedResult e)
		{
			// NOP
			return null;
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

	private final long numerator;
	private final long denominator;
}
