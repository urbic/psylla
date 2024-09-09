package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
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

	default public PsyRational rationalValue()
	{
		return this;
	}

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
	default public PsyRational psyToRational()
	{
		return this;
	}

	@Override
	default public PsyRational psyNeg()
	{
		return of(psyNumerator().psyNeg(), psyDenominator());
	}

	@Override
	default public PsyRealNumeric psyAdd(final PsyRealNumeric oRealNumeric)
	{
		return switch(oRealNumeric)
			{
				case PsyIntegral oIntegral->
					of((PsyIntegral)psyNumerator().psyAdd(psyDenominator().psyMul(oIntegral)),
						psyDenominator());
				case PsyRational oRational->
					of((PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator())
							.psyAdd(psyDenominator().psyMul(oRational.psyNumerator())),
						(PsyIntegral)psyDenominator().psyMul(oRational.psyDenominator()));
				case PsyReal oReal->
					new PsyReal(doubleValue()+oReal.doubleValue());
			};
	}

	@Override
	default public PsyRealNumeric psySub(final PsyRealNumeric oRealNumeric)
	{
		return switch(oRealNumeric)
			{
				case PsyIntegral oIntegral->
					of((PsyIntegral)psyNumerator().psySub(psyDenominator().psyMul(oIntegral)),
						psyDenominator());
				case PsyRational oRational->
					of((PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator())
							.psySub(psyDenominator().psyMul(oRational.psyNumerator())),
						(PsyIntegral)psyDenominator().psyMul(oRational.psyDenominator()));
				case PsyReal oReal->
					new PsyReal(doubleValue()+oReal.doubleValue());
			};
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
		return switch(oRealNumeric)
			{
				case PsyIntegral oIntegral->
					PsyRational.of((PsyIntegral)psyNumerator().psyMul(oIntegral),
						psyDenominator());
				case PsyRational oRational->
					PsyRational.of((PsyIntegral)psyNumerator().psyMul(oRational.psyNumerator()),
						(PsyIntegral)psyDenominator().psyMul(oRational.psyDenominator()));
				case PsyReal oReal->
					new PsyReal(doubleValue()*oReal.doubleValue());
			};
	}

	default public PsyRealNumeric psyDiv(final PsyRealNumeric oRealNumeric)
		throws PsyUndefinedResultException
	{
		return switch(oRealNumeric)
			{
				case PsyIntegral oIntegral->
					PsyRational.of(psyNumerator(),
						(PsyIntegral)psyDenominator().psyMul(oIntegral));
				case PsyRational oRational->
					PsyRational.of((PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator()),
						(PsyIntegral)psyDenominator().psyMul(oRational.psyNumerator()));
				case PsyReal oReal->
					new PsyReal(doubleValue()/oReal.doubleValue());
			};
	}

	/**
	*	Returns an {@code integral} representing this object modulo given modulus.
	*
	*	@param oRational the given modulus.
	*	@return an {@code integral} representing this object modulo given modulus.
	*	@throws PsyRangeCheckException when the modulus is negative.
	*	@throws PsyUndefinedResultException when the modulus is zero.
	*/
	default public PsyRational psyMod(final PsyRational oRational)
		throws PsyRangeCheckException, PsyUndefinedResultException
	{
		return of(
			((PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator()))
					.psyMod((PsyIntegral)psyDenominator().psyMul(oRational.psyNumerator())),
			(PsyIntegral)psyDenominator().psyMul(oRational.psyDenominator()));
	}

	// TODO javadoc
	default public PsyIntegral psyIdiv(final PsyRational oRational)
		throws PsyUndefinedResultException
	{
		return ((PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator()))
				.psyIdiv((PsyIntegral)oRational.psyNumerator().psyMul(psyDenominator()));
	}

	/**
	*	Returns a {@code rational} representing the greatest common divisor of this object and given
	*	object.
	*
	*	@param oRational the given object.
	*	@return the greatest common divisor.
	*/
	default public PsyRational psyGCD(final PsyRational oRational)
	{
		return of(
			(PsyIntegral)((PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator()))
					.psyGCD((PsyIntegral)psyDenominator().psyMul(oRational.psyNumerator())),
			(PsyIntegral)psyDenominator().psyMul(oRational.psyDenominator()));
	}

	@Override
	public PsyIntegral psyCeiling();

	@Override
	default public PsyRational psyAbs()
	{
		if(compareTo(PsyInteger.ZERO)<0)
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
	default public int compareTo(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyRational oRational)
			return psyNumerator().psyMul(oRational.psyDenominator())
					.compareTo(psyDenominator().psyMul(oRational.psyNumerator()));
		// TODO
		return Double.compare(doubleValue(), oNumeric.doubleValue());
	}

	public static PsyRational parseLiteral(final String image)
		throws PsySyntaxErrorException, PsyUndefinedResultException
	{
		var slashIndex=image.indexOf('/');
		if(slashIndex==-1)
			return PsyIntegral.parseLiteral(image);
		return of(PsyIntegral.parseLiteral(image.substring(0, slashIndex)),
				PsyIntegral.parseLiteral(image.substring(slashIndex)));
	}

	/**
	*	Context action of the {@code denominator} operator.
	*/
	@OperatorType("denominator")
	public static final ContextAction PSY_DENOMINATOR
		=ContextAction.<PsyRational>ofFunction(PsyRational::psyDenominator);

	/**
	*	Context action of the {@code gcd} operator.
	*/
	@OperatorType("gcd")
	public static final ContextAction PSY_GCD
		=ContextAction.<PsyRational, PsyRational>ofBiFunction(PsyRational::psyGCD);

	/**
	*	Context action of the {@code idiv} operator.
	*/
	@OperatorType("idiv")
	public static final ContextAction PSY_IDIV
		=ContextAction.<PsyRational, PsyRational>ofBiFunction(PsyRational::psyIdiv);

	/**
	*	Context action of the {@code mod} operator.
	*/
	@OperatorType("mod")
	public static final ContextAction PSY_MOD
		=ContextAction.<PsyRational, PsyRational>ofBiFunction(PsyRational::psyMod);

	/**
	*	Context action of the {@code numerator} operator.
	*/
	@OperatorType("numerator")
	public static final ContextAction PSY_NUMERATOR
		=ContextAction.<PsyRational>ofFunction(PsyRational::psyNumerator);

}
