package coneforest.psylla.core;

import coneforest.psylla.*;
import java.math.BigInteger;

/**
*	A representation of {@code integral}.
*/
@Type("integral")
public interface PsyIntegral
	extends
		PsyBitwise<PsyIntegral>,
		PsyRational
{
	@Override
	default public PsyIntegral psyNumerator()
	{
		return this;
	}

	@Override
	default public PsyIntegral psyDenominator()
	{
		return ONE;
	}

	@Override
	public PsyIntegral psyAbs();

	@Override
	public PsyIntegral psyNeg();

	@Override
	default public PsyIntegral psyFloor()
	{
		return this;
	}

	@Override
	default public PsyIntegral psyCeiling()
	{
		return this;
	}

	@Override
	default public PsyIntegral psyRound()
	{
		return this;
	}

	public PsyIntegral psyIdiv(final PsyIntegral oIntegral)
		throws PsyUndefinedResultException;

	public PsyIntegral psyMod(final PsyIntegral oIntegral)
		throws PsyUndefinedResultException, PsyRangeCheckException;

	/**
	*	Returns an {@code integral} representing the greatest common divisor of
	*	this object and given object.
	*
	*	@param oIntegral given object.
	*	@return the greatest common divisor.
	*/
	default public PsyIntegral psyGCD(final PsyIntegral oIntegral)
	{
		var oX=psyAbs();
		var oY=oIntegral.psyAbs();
		if(oY.psyIsZero().booleanValue())
			return oX;
		while(oX.psyNonZero().booleanValue())
		{
			if(oX.psyGt(oY).booleanValue())
			{
				var oT=oX;
				oX=oY;
				oY=oT;
			}
			try
			{
				oY=oY.psyMod(oX);
			}
			catch(final PsyUndefinedResultException|PsyRangeCheckException e)
			{
				// NOP
			}
		}
		return oY;
	}

	/**
	*	Returns an {@code integral} representing the least common multiple of
	*	this object and given object.
	*
	*	@param oIntegral given object.
	*	@return the least common multiple.
	*/
	default PsyIntegral psyLCM(final PsyIntegral oIntegral)
		throws PsyUndefinedResultException
	{
		if(psyIsZero().booleanValue() || oIntegral.psyIsZero().booleanValue())
			return PsyInteger.ZERO;
		return ((PsyIntegral)psyMul(oIntegral)).psyIdiv(psyGCD(oIntegral));
	}

	@Override
	default public PsyIntegral psyToIntegral()
	{
		return this;
	}

	public static PsyInteger of(final long longValue)
	{
		return PsyInteger.of(longValue);
	}

	public static PsyIntegral of(final BigInteger bigIntegerValue)
	{
		try
		{
			return PsyInteger.of(bigIntegerValue.longValueExact());
		}
		catch(final ArithmeticException ex)
		{
			return new PsyBigInteger(bigIntegerValue);
		}
	}

	public static PsyIntegral parseLiteral(final String image)
		throws PsySyntaxErrorException
	{
		int radix=10;
		var prefixlessImage=image;
		if(image.length()>1 && image.charAt(1)=='`')
		{
			prefixlessImage=image.substring(2);
			switch(image.charAt(0))
			{
				case 'X':
				case 'x':
					radix=16;
					break;
				case 'O':
				case 'o':
					radix=8;
					break;
				case 'B':
				case 'b':
					radix=2;
					break;
				case 'C':
				case 'c':
					return parseCharLiteral(prefixlessImage);
			}
		}
		try
		{
			try
			{
				return PsyInteger.of(Long.parseLong(prefixlessImage, radix));
			}
			catch(final NumberFormatException ex)
			{
				return new PsyBigInteger(new BigInteger(prefixlessImage, radix));
			}
		}
		catch(final NumberFormatException ex)
		{
			throw new PsySyntaxErrorException();
		}
	}

	private static PsyInteger parseCharLiteral(final String image)
		throws PsySyntaxErrorException
	{
		switch(image.charAt(0))
		{
			case '\\':
				switch(image.charAt(1))
				{
					case '0':
						return PsyInteger.of('\u0000');
					case 'a':
						return PsyInteger.of('\u0007');
					case 'n':
						return PsyInteger.of('\n');
					case 'r':
						return PsyInteger.of('\r');
					case 't':
						return PsyInteger.of('\t');
					case 'v':
						return PsyInteger.of('\u000B');
					case 'f':
						return PsyInteger.of('\f');
					case 'e':
						return PsyInteger.of('\u001B');
					case '\\':
						return PsyInteger.of('\\');
					case 'u':
						return PsyInteger.of(Integer.valueOf(image.substring(2, 6), 16));
					case 'c':
						{
							final var ch=image.charAt(2);
							return PsyInteger.of(ch+(ch<64? 64: -64));
						}
					case 'x':
						try
						{
							return PsyInteger.of(Integer.valueOf(
									image.substring(3, image.length()-1), 16));
						}
						catch(final IllegalArgumentException ex)
						{
							throw new PsySyntaxErrorException();
						}
					case 'N':
						try
						{
							return PsyInteger.of(
									Character.codePointOf(image.substring(3, image.length()-1)));
						}
						catch(final IllegalArgumentException ex)
						{
							throw new PsySyntaxErrorException();
						}
				}
			default:
				return PsyInteger.of(image.charAt(0));
		}
	}

	public static final PsyInteger ZERO=PsyInteger.ZERO;
	public static final PsyInteger ONE=PsyInteger.ONE;
	public static final PsyInteger TWO=PsyInteger.TWO;
	public static final PsyInteger MINUS_ONE=PsyInteger.MINUS_ONE;

	/**
	*	Context action of the {@code idiv} operator.
	*/
	@OperatorType("idiv")
	public static final ContextAction PSY_IDIV
		=ContextAction.<PsyIntegral, PsyIntegral>ofBiFunction(PsyIntegral::psyIdiv);

	/**
	*	Context action of the {@code mod} operator.
	*/
	@OperatorType("mod")
	public static final ContextAction PSY_MOD
		=ContextAction.<PsyIntegral, PsyIntegral>ofBiFunction(PsyIntegral::psyMod);

	/**
	*	Context action of the {@code gcd} operator.
	*/
	@OperatorType("gcd")
	public static final ContextAction PSY_GCD
		=ContextAction.<PsyIntegral, PsyIntegral>ofBiFunction(PsyIntegral::psyGCD);

	/**
	*	Context action of the {@code lcm} operator.
	*/
	@OperatorType("lcm")
	public static final ContextAction PSY_LCM
		=ContextAction.<PsyIntegral, PsyIntegral>ofBiFunction(PsyIntegral::psyLCM);
}
