package coneforest.psylla.core;

import coneforest.psylla.*;
import java.math.BigInteger;

/**
*	The representation of {@code rational}.
*/
@Type("rational")
public sealed interface PsyRational
	extends PsyRealNumeric
	permits
		PsyIntegral,
		PsyFraction,
		PsyBigFraction
{
	public BigInteger bigIntegerValue();

	/**
	*	Returns an {@code integral} numerator of this fraction.
	*
	*	@return the numerator.
	*/
	public PsyIntegral psyNumerator();

	/**
	*	Returns an {@code integral} denominator of this fraction.
	*
	*	@return the denominator.
	*/
	public PsyIntegral psyDenominator();

	@Override
	default public PsyRational psyNeg()
	{
		return of(psyNumerator().psyNeg(), psyDenominator());
	}

	@Override
	default public PsyRealNumeric psyAdd(final PsyRealNumeric oRealNumeric)
	{
		if(oRealNumeric instanceof PsyIntegral oIntegral)
			return of(
				(PsyIntegral)psyNumerator().psyAdd(psyDenominator().psyMul(oIntegral)),
				psyDenominator());
		if(oRealNumeric instanceof PsyRational oRational)
			return of(
				(PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator())
						.psyAdd(psyDenominator().psyMul(oRational.psyNumerator())),
				(PsyIntegral)psyDenominator().psyMul(oRational.psyDenominator()));
		if(oRealNumeric instanceof PsyReal oReal)
			return new PsyReal(doubleValue()+oReal.doubleValue());
		throw new ClassCastException();
	}

	@Override
	default public PsyRealNumeric psySub(final PsyRealNumeric oRealNumeric)
	{
		if(oRealNumeric instanceof PsyIntegral oIntegral)
			return of(
					(PsyIntegral)psyNumerator().psySub(psyDenominator().psyMul(oIntegral)),
					psyDenominator());
		if(oRealNumeric instanceof PsyRational oRational)
			return of(
				(PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator())
						.psySub(psyDenominator().psyMul(oRational.psyNumerator())),
				(PsyIntegral)psyDenominator().psyMul(oRational.psyDenominator()));
		if(oRealNumeric instanceof PsyReal oReal)
			return new PsyReal(doubleValue()+oReal.doubleValue());
		throw new ClassCastException();
	}

	@Override
	default public PsyRational psyReciprocal()
		throws PsyUndefinedResultException
	{
		try
		{
			return of(psyDenominator(), psyNumerator());
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyUndefinedResultException();
		}
	}

	@Override
	default public PsyRealNumeric psyMul(final PsyRealNumeric oRealNumeric)
	{
		if(oRealNumeric instanceof PsyIntegral oIntegral)
			return PsyRational.of(
					(PsyIntegral)psyNumerator().psyMul(oIntegral),
					psyDenominator());
		if(oRealNumeric instanceof PsyRational oRational)
			return PsyRational.of(
					(PsyIntegral)psyNumerator().psyMul(oRational.psyNumerator()),
					(PsyIntegral)psyDenominator().psyMul(oRational.psyDenominator()));
		if(oRealNumeric instanceof PsyReal oReal)
			return new PsyReal(doubleValue()*oReal.doubleValue());
		throw new ClassCastException();
	}

	default public PsyRealNumeric psyDiv(final PsyRealNumeric oRealNumeric)
		throws PsyUndefinedResultException
	{
		if(oRealNumeric instanceof PsyIntegral oIntegral)
			return PsyRational.of(psyNumerator(),
					(PsyIntegral)psyDenominator().psyMul(oIntegral));
		if(oRealNumeric instanceof PsyRational oRational)
			return PsyRational.of(
					(PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator()),
					(PsyIntegral)psyDenominator().psyMul(oRational.psyNumerator()));
		if(oRealNumeric instanceof PsyReal oReal)
			return new PsyReal(doubleValue()*oReal.doubleValue());
		throw new ClassCastException();
	}

	@Override
	public PsyIntegral psyCeiling();

	@Override
	default public PsyRational psyAbs()
	{
		if(psyCmp(PsyInteger.ZERO).equals(PsyInteger.MINUS_ONE))
			return of(psyNumerator().psyNeg(), psyDenominator());
		return this;
	}

	@Override
	public PsyIntegral psyFloor();

	public static PsyRational of(final PsyIntegral oNumerator, final PsyIntegral oDenominator)
	{
		try
		{
			if(oNumerator.psyMod(oDenominator.psyAbs()).psyIsZero().booleanValue())
				return oNumerator.psyIdiv(oDenominator);
		}
		catch(final PsyRangeCheckException|PsyUndefinedResultException e)
		{
			throw new IllegalArgumentException();
		}
		if(oNumerator instanceof PsyInteger && oDenominator instanceof PsyInteger)
			return PsyFraction.of(oNumerator.longValue(), oDenominator.longValue());
		else
			return PsyBigFraction.of(oNumerator.bigIntegerValue(), oDenominator.bigIntegerValue());
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
