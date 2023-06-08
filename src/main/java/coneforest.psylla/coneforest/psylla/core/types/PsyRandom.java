package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyRangeCheck;
import coneforest.psylla.core.errors.PsyTypeCheck;
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
		throws PsyError
	{
		if(oRealNumeric instanceof PsyReal)
			return new PsyReal(oRealNumeric.doubleValue()*random.nextDouble());
		else if(oRealNumeric instanceof PsyInteger)
		{
			final var numeric=oRealNumeric.longValue();
			if(numeric>Long.MAX_VALUE)
				throw new PsyRangeCheck();
			try
			{
				return PsyInteger.of(random.nextLong(numeric));
			}
			catch(final IllegalArgumentException ex)
			{
				throw new PsyRangeCheck();
			}
		}
		else if(oRealNumeric instanceof PsyBigInteger oBigInteger)
		{
			var bi=oBigInteger.bigIntegerValue();
			java.math.BigInteger rbi;
			do
				rbi=new java.math.BigInteger(bi.bitLength(), random);
			while(rbi.compareTo(bi)>=0);
			return PsyIntegral.of(rbi);
		}
		else
			throw new PsyTypeCheck();
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

}
