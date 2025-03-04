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
	*	Context action of the {@code lcm} operator.
	*/
	@OperatorType("lcm")
	public static final ContextAction PSY_LCM
		=ContextAction.<PsyRational, PsyRational>ofBiFunction(PsyRational::psyLCM);

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

	public BigInteger bigIntegerValue();

	public default PsyRational rationalValue()
	{
		return this;
	}

	/**
	*	{@return an {@code integral} numerator of this fraction}
	*/
	public PsyIntegral psyNumerator();

	/**
	*	{@return an {@code integral} denominator of this fraction}
	*/
	public PsyIntegral psyDenominator();

	@Override
	public default PsyRational psyToRational()
	{
		return this;
	}

	@Override
	public default PsyIntegral psyRound()
	{
		final var oIntPart=psyFloor();
		final var oFractPart=psySub(oIntPart);
		return PsyInteger.TWO.psyMul(oFractPart).compareTo(PsyInteger.ONE)<0?
				oIntPart: (PsyIntegral)oIntPart.psyAdd(PsyInteger.ONE);
	}

	@Override
	public default PsyIntegral psyToIntegral()
	{
		return psyRound();
	}

	@Override
	public default PsyRational psyNeg()
	{
		return of(psyNumerator().psyNeg(), psyDenominator());
	}

	@Override
	public default PsyRealNumeric psyAdd(final PsyRealNumeric oRealNumeric)
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
	public default PsyRealNumeric psySub(final PsyRealNumeric oRealNumeric)
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
	public default PsyRational psyReciprocal()
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
	public default PsyRealNumeric psyMul(final PsyRealNumeric oRealNumeric)
	{
		return switch(oRealNumeric)
			{
				case PsyIntegral oIntegral->
					of((PsyIntegral)psyNumerator().psyMul(oIntegral),
						psyDenominator());
				case PsyRational oRational->
					of((PsyIntegral)psyNumerator().psyMul(oRational.psyNumerator()),
						(PsyIntegral)psyDenominator().psyMul(oRational.psyDenominator()));
				case PsyReal oReal->
					new PsyReal(doubleValue()*oReal.doubleValue());
			};
	}

	public default PsyRealNumeric psyDiv(final PsyRealNumeric oRealNumeric)
		throws PsyUndefinedResultException
	{
		return switch(oRealNumeric)
			{
				case PsyIntegral oIntegral->
					of(psyNumerator(),
						(PsyIntegral)psyDenominator().psyMul(oIntegral));
				case PsyRational oRational->
					of((PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator()),
						(PsyIntegral)psyDenominator().psyMul(oRational.psyNumerator()));
				case PsyReal oReal->
					new PsyReal(doubleValue()/oReal.doubleValue());
			};
	}

	/**
	*	{@return an {@code integral} representing this object modulo given modulus}
	*
	*	@param oRational the given modulus.
	*	@throws PsyRangeCheckException when the modulus is negative.
	*	@throws PsyUndefinedResultException when the modulus is zero.
	*/
	public default PsyRational psyMod(final PsyRational oRational)
		throws PsyRangeCheckException, PsyUndefinedResultException
	{
		return of(
			((PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator()))
					.psyMod((PsyIntegral)psyDenominator().psyMul(oRational.psyNumerator())),
			(PsyIntegral)psyDenominator().psyMul(oRational.psyDenominator()));
	}

	/**
	*	{@return the quotient of the integer division of this {@code rational} by the {@code
	*	rational} divisor}
	*
	*	@param oRational the divisor.
	*	@throws PsyUndefinedResultException when the divisor is zero.
	*/
	public default PsyIntegral psyIdiv(final PsyRational oRational)
		throws PsyUndefinedResultException
	{
		return ((PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator()))
				.psyIdiv((PsyIntegral)oRational.psyNumerator().psyMul(psyDenominator()));
	}

	/**
	*	{@return a {@code rational} representing the greatest common divisor of this object and
	*	given object}
	*
	*	@param oRational the given object.
	*/
	public default PsyRational psyGCD(final PsyRational oRational)
	{
		return of((PsyIntegral)((PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator()))
						.psyGCD((PsyIntegral)psyDenominator().psyMul(oRational.psyNumerator())),
				(PsyIntegral)psyDenominator().psyMul(oRational.psyDenominator()));
	}

	/**
	*	{@return a {@code rational} representing the least common multiplier of this object and
	*	given object}
	*
	*	@param oRational the given object.
	*/
	public default PsyRational psyLCM(final PsyRational oRational)
	{
		return of(((PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator()))
						.psyLCM((PsyIntegral)psyDenominator().psyMul(oRational.psyNumerator())),
				(PsyIntegral)psyDenominator().psyMul(oRational.psyDenominator()));
	}

	@Override
	public PsyIntegral psyCeiling();

	@Override
	public default PsyRational psyAbs()
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
	public default int compareTo(final PsyRealNumeric oNumeric)
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
		final var slashIndex=image.indexOf(':');
		if(slashIndex==-1)
			return PsyIntegral.parseLiteral(image);
		return of(PsyIntegral.parseLiteral(image.substring(0, slashIndex)),
				PsyIntegral.parseLiteral(image.substring(slashIndex+1)));
	}
}
