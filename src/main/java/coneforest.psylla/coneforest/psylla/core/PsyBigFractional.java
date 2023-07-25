package coneforest.psylla.core;

import coneforest.psylla.*;
import java.math.BigInteger;

/**
*	A representation of {@code bigfractional}.
*/
@Type("bigfractional")
public final class PsyBigFractional
	implements PsyRational
{
	private PsyBigFractional(final BigInteger numerator, final BigInteger denominator)
	{
		this.numerator=numerator;
		this.denominator=denominator;
	}

	public static PsyBigFractional of(final BigInteger numerator, final BigInteger denominator)
	{
		return new PsyBigFractional(numerator, denominator);
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
		System.err.println("PsyBigFractional::psyToIntegral");
		return null;
	}

	@Override
	public PsyInteger psyCmp(final PsyRealNumeric oRealNumeric)
	{
		// TODO
		System.err.println("PsyBigFractional::psyCmp");
		return null;
	}

	@Override
	public PsyReal psyRound()
	{
		// TODO
		System.err.println("PsyBigFractional::psyRound");
		return null;
	}

	@Override
	public PsyRational psyAbs()
	{
		// TODO
		System.err.println("PsyBigFractional::psyAbs");
		return null;
	}

	@Override
	public PsyRealNumeric psyMul(final PsyRealNumeric oRealNumeric)
	{
		// TODO
		System.err.println("PsyBigFractional::psyMul");
		return null;
	}

	@Override
	public PsyRealNumeric psyDiv(final PsyRealNumeric oRealNumeric)
	{
		// TODO
		System.err.println("PsyBigFractional::psyDiv");
		return null;
	}

	@Override
	public PsyRealNumeric psyAdd(final PsyRealNumeric oRealNumeric)
	{
		// TODO
		return null;
	}

	/*
	@Override
	public PsyRealNumeric psySub(final PsyRealNumeric oRealNumeric)
	{
		if(oRealNumeric instanceof PsyRational oRational)
		{
			try
			{
				return psyNumerator().psyMul(oRational.psyDenominator()).psySub(
						psyDenominator().psyMul(oRational.psyNumerator()))
						.psyDiv(psyDenominator().psyMul(oRational.psyDenominator()));
			}
			catch(final PsyUndefinedResultException e)
			{
				throw new AssertionError();
			}
		}
		else
		{
			// NOP
			System.err.println("PsyBigFractional::psySub: "+toSyntaxString()+" - "+oRealNumeric.toSyntaxString());
			return null;
		}
	}
	*/

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
