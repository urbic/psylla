package coneforest.psi.core;

/**
 *	A representation of Ψ-{@code random} object.
 */
public class PsiRandom
	implements PsiObject
{
	/**
	 *	@return a string {@code "random"}.
	 */
	@Override
	public String getTypeName()
	{
		return "random";
	}

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

	private java.util.Random random=new java.util.Random();
}
