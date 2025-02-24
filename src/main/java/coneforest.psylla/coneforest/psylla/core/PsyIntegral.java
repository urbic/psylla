package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.math.BigInteger;

/**
*	The representation of {@code integral}.
*/
@Type("integral")
public sealed interface PsyIntegral
	extends
		PsyBitwise<PsyIntegral>,
		PsyRational
	permits
		PsyBigInteger,
		PsyInteger
{
	@Override
	public default PsyIntegral psyNumerator()
	{
		return this;
	}

	@Override
	public default PsyIntegral psyDenominator()
	{
		return PsyInteger.ONE;
	}

	@Override
	public PsyIntegral psyAbs();

	@Override
	public PsyIntegral psyNeg();

	@Override
	public default PsyIntegral psyFloor()
	{
		return this;
	}

	@Override
	public default PsyIntegral psyCeiling()
	{
		return this;
	}

	@Override
	public default PsyIntegral psyRound()
	{
		return this;
	}

	@Override
	public default PsyIntegral psyIdiv(final PsyRational oRational)
		throws PsyUndefinedResultException
	{
		return ((PsyIntegral)psyMul(oRational.psyDenominator()))
				.psyIdiv(oRational.psyNumerator());
	}

	@Override
	public PsyIntegral psyMod(final PsyRational oRational)
		throws PsyRangeCheckException, PsyUndefinedResultException;

	@Override
	public default PsyRational psyGCD(final PsyRational oRational)
	{
		return switch(oRational)
			{
				case PsyIntegral oIntegral->
					{
						var oX=psyAbs();
						var oY=oIntegral.psyAbs();
						if(oY.isZero())
							yield oX;
						while(!oX.isZero())
						{
							if(oX.compareTo(oY)>0)
							{
								final var oT=oX;
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
						yield oY;
					}
				default->PsyRational.of(
						(PsyIntegral)((PsyIntegral)psyMul(oRational.psyDenominator()))
								.psyGCD(oRational.psyNumerator()),
						oRational.psyDenominator());
			};
	}

	/**
	*	Returns an {@code integral} representing the least common multiple of this object and given
	*	object.
	*
	*	@param oIntegral given object.
	*	@return the least common multiple of this object and given object.
	*/
	default PsyIntegral psyLCM(final PsyIntegral oIntegral)
	{
		if(isZero() || oIntegral.isZero())
			return PsyInteger.ZERO;
		try
		{
			return ((PsyIntegral)psyMul(oIntegral)).psyIdiv(psyGCD(oIntegral)).psyAbs();
		}
		catch(final PsyUndefinedResultException e)
		{
			throw new AssertionError();
		}
	}

	@Override
	public default PsyIntegral psyToIntegral()
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

	/**
	*	{@return the {@code integral} object obtained as a result of parsing the literal token
	*	image}
	*
	*	@param image the literal token image.
	*	@throws PsySyntaxErrorException when the literal image is syntactically incorrect.
	*/
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
				case 'X', 'x':
					radix=16;
					break;
				case 'O', 'o':
					radix=8;
					break;
				case 'B', 'b':
					radix=2;
					break;
				case 'C', 'c':
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
							return PsyInteger.of(
									Integer.valueOf(image.substring(3, image.length()-1), 16));
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
					default:
						throw new PsySyntaxErrorException();
				}
			default:
				return PsyInteger.of(image.charAt(0));
		}
	}
}
