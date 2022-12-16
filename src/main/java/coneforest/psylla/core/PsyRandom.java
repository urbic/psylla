package coneforest.psylla.core;
import coneforest.psylla.*;

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

	public PsyRealNumeric psyUniformDeviate(final PsyRealNumeric oNumeric)
		throws PsyErrorException
	{
		if(oNumeric instanceof PsyReal)
			return new PsyReal(oNumeric.doubleValue()*random.nextDouble());
		else if(oNumeric instanceof PsyInteger)
		{
			final var numeric=oNumeric.longValue();
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
		else if(oNumeric instanceof PsyBigInteger)
		{
			var bi=((PsyBigInteger)oNumeric).bigIntegerValue();
			java.math.BigInteger rbi;
			do
			{
				rbi=new java.math.BigInteger(bi.bitLength(), random);
			}
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

	public PsyReal psyNormalDeviate(final PsyRealNumeric oNumeric)
	{
		return new PsyReal(oNumeric.doubleValue()*random.nextGaussian());
	}

	private final java.util.Random random=new java.util.Random();

}
