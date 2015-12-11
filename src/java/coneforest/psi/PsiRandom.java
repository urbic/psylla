package coneforest.psi;

/**
 *	A representation of Ψ-{@code random} object.
 */
public class PsiRandom
	implements PsiObject
{
	public PsiRandom()
	{
		random=new java.util.Random();
	}

	/**
	 *	@return a string {@code "random"}.
	 */
	@Override
	public String getTypeName()
	{
		return "random";
	}

	public void psiSetSeed(PsiInteger seed)
	{
		random.setSeed(seed.longValue());
	}

	public PsiNumeric psiUniformDeviate(PsiNumeric numeric)
		throws PsiException
	{
		if(numeric instanceof PsiReal)
			return new PsiReal(numeric.doubleValue()*random.nextDouble());
		else if(numeric instanceof PsiInteger)
		{
			int numericValue=numeric.intValue();
			if(numericValue>Integer.MAX_VALUE)
				throw new PsiRangeCheckException();
			try
			{
				return PsiInteger.valueOf(random.nextInt(numericValue));
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

	public PsiReal psiNormalDeviate(PsiNumeric numeric)
	{
		return new PsiReal(numeric.doubleValue()*random.nextGaussian());
	}

	private java.util.Random random;
}
