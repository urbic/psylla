package coneforest.psylla.core;

import coneforest.psylla.*;
import java.math.BigInteger;

/**
*	A representation of {@code rational}.
*/
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
		throws PsyUndefinedResultException
	{
		if(oDenominator.psyIsZero().booleanValue())
			throw new PsyUndefinedResultException();
		try
		{
			if(oNumerator.psyMod(oDenominator.psyAbs()).psyIsZero().booleanValue())
				return oNumerator.psyIdiv(oDenominator);
		}
		catch(final PsyRangeCheckException e)
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
		throws PsySyntaxErrorException, PsyUndefinedResultException
	{
		var colonIndex=image.indexOf(':');
		if(colonIndex==-1)
			return PsyIntegral.parseLiteral(image);
		return of(PsyIntegral.parseLiteral(image.substring(0, colonIndex)),
				PsyIntegral.parseLiteral(image.substring(colonIndex)));
	}

	/**
	*	Context action of the {@code numerator} operator.
	*/
	@OperatorType("numerator")
	public static final ContextAction PSY_NUMERATOR
		=ContextAction.<PsyRational>ofFunction(PsyRational::psyNumerator);

	/**
	*	Context action of the {@code denominator} operator.
	*/
	@OperatorType("denominator")
	public static final ContextAction PSY_DENOMINATOR
		=ContextAction.<PsyRational>ofFunction(PsyRational::psyDenominator);
}
