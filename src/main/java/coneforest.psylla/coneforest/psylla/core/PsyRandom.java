package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.math.BigInteger;
import java.util.Random;

/**
*	The representation of {@code random}, a generator of pseudorandom objects.
*/
@Type("random")
public class PsyRandom
	implements PsyObject
{
	/**
	*	Context action of the {@code random} operator.
	*/
	@OperatorType("random")
	public static final ContextAction PSY_RANDOM
		=ContextAction.ofSupplier(PsyRandom::new);

	/**
	*	Context action of the {@code uniformboolean} operator.
	*/
	@OperatorType("uniformboolean")
	public static final ContextAction PSY_RANDOMBOOLEAN
		=ContextAction.<PsyRandom>ofFunction(PsyRandom::psyUniformBoolean);

	/**
	*	Context action of the {@code uniformdeviate} operator.
	*/
	@OperatorType("uniformdeviate")
	public static final ContextAction PSY_UNIFORMDEVIATE
		=ContextAction.<PsyRandom, PsyRealNumeric>ofBiFunction(PsyRandom::psyUniformDeviate);

	private final Random random=new Random();

	public PsyRandom()
	{
	}

	/**
	*	Sets the seed of this {@code random} generator.
	*
	*	@param oSeed the given seed.
	*/
	public void psySetSeed(final PsyInteger oSeed)
	{
		random.setSeed(oSeed.longValue());
	}

	public PsyRealNumeric psyUniformDeviate(final PsyRealNumeric oRealNumeric)
		throws PsyRangeCheckException, PsyTypeCheckException
	{
		return switch(oRealNumeric)
			{
				case PsyReal oReal->new PsyReal(oReal.doubleValue()*random.nextDouble());
				case PsyInteger oInteger->
					{
						final var numericValue=oRealNumeric.longValue();
						if(numericValue>Long.MAX_VALUE)	// TODO
							throw new PsyRangeCheckException();
						try
						{
							yield PsyInteger.of(random.nextLong(numericValue));
						}
						catch(final IllegalArgumentException ex)
						{
							throw new PsyRangeCheckException();
						}
					}
				case PsyBigInteger oBigInteger->
					{
						final var bi=oBigInteger.bigIntegerValue();
						BigInteger rbi;
						do
							rbi=new BigInteger(bi.bitLength(), random);
						while(rbi.compareTo(bi)>=0);
						yield PsyIntegral.of(rbi);
					}
				default->throw new PsyTypeCheckException();	// TODO PsyRational
			};
	}

	public PsyBoolean psyUniformBoolean()
	{
		return PsyBoolean.of(random.nextBoolean());
	}

	public PsyReal psyNormalDeviate(final PsyRealNumeric oRealNumeric)
	{
		return new PsyReal(oRealNumeric.doubleValue()*random.nextGaussian());
	}
}
