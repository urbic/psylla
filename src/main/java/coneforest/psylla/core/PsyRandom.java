package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code random}, a generator of pseudorandom objects.
*/
@coneforest.psylla.Type("random")
public class PsyRandom
	implements PsyObject
{

	/**
	*	Sets the seed of this random generator.
	*
	*	@param oSeed the given seed.
	*/
	public void psySetSeed(final PsyInteger oSeed)
	{
		random.setSeed(oSeed.longValue());
	}

	public PsyRealNumeric psyUniformDeviate(final PsyRealNumeric oNumeric)
		throws PsyException
	{
		if(oNumeric instanceof PsyReal)
			return new PsyReal(oNumeric.doubleValue()*random.nextDouble());
		else if(oNumeric instanceof PsyInteger)
		{
			final int numeric=oNumeric.intValue();
			if(numeric>Integer.MAX_VALUE)
				throw new PsyRangeCheckException();
			try
			{
				return PsyInteger.valueOf(random.nextInt(numeric));
			}
			catch(final IllegalArgumentException e)
			{
				throw new PsyRangeCheckException();
			}
		}
		else
			throw new PsyTypeCheckException();
	}

	public PsyBoolean psyUniformBoolean()
	{
		return PsyBoolean.valueOf(random.nextBoolean());
	}

	public PsyReal psyNormalDeviate(final PsyRealNumeric oNumeric)
	{
		return new PsyReal(oNumeric.doubleValue()*random.nextGaussian());
	}

	private final java.util.Random random=new java.util.Random();
}
