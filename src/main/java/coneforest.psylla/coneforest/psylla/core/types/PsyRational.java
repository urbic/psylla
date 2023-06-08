package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyRangeCheck;
import coneforest.psylla.core.errors.PsySyntaxError;
import coneforest.psylla.core.errors.PsyUndefinedResult;
import java.math.BigInteger;

@Type("rational")
public interface PsyRational
	extends PsyRealNumeric
{
	public BigInteger bigIntegerValue();

	/**
	*	Returns an {@code integral} representing a numerator of this fraction.
	*
	*	@return the numerator.
	*/
	public PsyIntegral psyNumerator();

	/**
	*	Returns an {@code integral} representing a denominator of this fraction.
	*
	*	@return the denominator.
	*/
	public PsyIntegral psyDenominator();

	@Override
	public PsyIntegral psyCeiling();

	@Override
	public PsyIntegral psyFloor();

	public static PsyRational of(final PsyIntegral oNumerator, final PsyIntegral oDenominator)
		throws PsyUndefinedResult
	{
		if(oDenominator.psyIsZero().booleanValue())
			throw new PsyUndefinedResult();
		try
		{
			if(oNumerator.psyMod(oDenominator.psyAbs()).psyIsZero().booleanValue())
				return oNumerator.psyIdiv(oDenominator);
		}
		catch(final PsyRangeCheck e)
		{
			// NOP
		}
		if(oNumerator instanceof PsyInteger && oDenominator instanceof PsyInteger)
			return PsyFractional.of(oNumerator.longValue(), oDenominator.longValue());
		else
			return PsyBigFractional.of(oNumerator.bigIntegerValue(), oDenominator.bigIntegerValue());
	}

	@Override
	default public int cmp(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyRational oRational)
			return psyNumerator().psyMul(oRational.psyDenominator())
					.cmp(psyDenominator().psyMul(oRational.psyNumerator()));
		return Double.compare(doubleValue(), oNumeric.doubleValue());
	}

	public static PsyRational parseLiteral(final String image)
		throws PsySyntaxError, PsyUndefinedResult
	{
		var colonIndex=image.indexOf(':');
		if(colonIndex==-1)
			return PsyIntegral.parseLiteral(image);
		return of(PsyIntegral.parseLiteral(image.substring(0, colonIndex)),
				PsyIntegral.parseLiteral(image.substring(colonIndex)));
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyRational>("numerator", PsyRational::psyNumerator),
			new PsyOperator.Arity11<PsyRational>("denominator", PsyRational::psyDenominator),
		};
}
