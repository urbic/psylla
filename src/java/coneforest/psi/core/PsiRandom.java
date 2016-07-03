package coneforest.psi.core;

/**
*	A representation of Ψ-{@code random}, a generator of pseudorandom objects.
*/
public class PsiRandom
	implements PsiObject
{
	/**
	*	@return a string {@code "random"}.
	*/
	@Override
	public String typeName()
	{
		return "random";
	}

	/**
	*	Sets the seed of this random generator.
	*
	*	@param oSeed the given seed.
	*/
	public void psiSetSeed(final PsiInteger oSeed)
	{
		random.setSeed(oSeed.longValue());
	}

	public PsiNumeric psiUniformDeviate(final PsiNumeric oNumeric)
		throws PsiException
	{
		if(oNumeric instanceof PsiReal)
			return new PsiReal(oNumeric.doubleValue()*random.nextDouble());
		else if(oNumeric instanceof PsiInteger)
		{
			final int numeric=oNumeric.intValue();
			if(numeric>Integer.MAX_VALUE)
				throw new PsiRangeCheckException();
			try
			{
				return PsiInteger.valueOf(random.nextInt(numeric));
			}
			catch(IllegalArgumentException e)
			{
				throw new PsiRangeCheckException();
			}
		}
		else
			throw new PsiTypeCheckException();
	}

	public PsiBoolean psiUniformBoolean()
	{
		return PsiBoolean.valueOf(random.nextBoolean());
	}

	public PsiReal psiNormalDeviate(final PsiNumeric oNumeric)
	{
		return new PsiReal(oNumeric.doubleValue()*random.nextGaussian());
	}

	private final java.util.Random random=new java.util.Random();
}
