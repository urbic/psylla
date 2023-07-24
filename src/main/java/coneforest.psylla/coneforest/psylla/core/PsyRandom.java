package coneforest.psylla.core;

import coneforest.psylla.*;
import java.math.BigInteger;
import java.util.Random;

/**
*	A representation of {@code random}, a generator of pseudorandom objects.
*/
@Type("random")
public class PsyRandom
	implements PsyObject
{

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
		if(oRealNumeric instanceof PsyReal)
			return new PsyReal(oRealNumeric.doubleValue()*random.nextDouble());
		else if(oRealNumeric instanceof PsyInteger)
		{
			final var numeric=oRealNumeric.longValue();
			if(numeric>Long.MAX_VALUE)
				throw new PsyRangeCheckException();
			try
			{
				return PsyInteger.of(random.nextLong(numeric));
			}
			catch(final IllegalArgumentException ex)
			{
				throw new PsyRangeCheckException();
			}
		}
		else if(oRealNumeric instanceof PsyBigInteger oBigInteger)
		{
			var bi=oBigInteger.bigIntegerValue();
			BigInteger rbi;
			do
				rbi=new BigInteger(bi.bitLength(), random);
			while(rbi.compareTo(bi)>=0);
			return PsyIntegral.of(rbi);
		}
		else
			throw new PsyTypeCheckException();
	}

	public PsyBoolean psyUniformBoolean()
	{
		return PsyBoolean.of(random.nextBoolean());
	}

	public PsyReal psyNormalDeviate(final PsyRealNumeric oRealNumeric)
	{
		return new PsyReal(oRealNumeric.doubleValue()*random.nextGaussian());
	}

	private final Random random=new Random();

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
}
