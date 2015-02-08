package coneforest.psi;

/**
 *	A representation of Ψ <code class="type">random</code> object.
 */
public class PsiRandom
	extends PsiAbstractObject
	implements PsiAtomic
{
	public PsiRandom()
	{
		random=new java.util.Random();
	}

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
				throw new PsiException("rangecheck");
			try
			{
				return PsiInteger.valueOf(random.nextInt(numericValue));
			}
			catch(IllegalArgumentException e)
			{
				throw new PsiException("rangecheck");
			}
		}
		else
			throw new PsiException("typecheck");
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
