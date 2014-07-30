package coneforest.psi;

public class PsiRandom
	extends PsiObject
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
		random.setSeed(seed.getValue());
	}

	public PsiNumeric psiUniformDeviate(PsiNumeric numeric)
		throws PsiException
	{
		if(numeric instanceof PsiReal)
			return new PsiReal(numeric.getValue().doubleValue()*random.nextDouble());
		else if(numeric instanceof PsiInteger)
		{
			int numericValue=numeric.getValue().intValue();
			if(numericValue>Integer.MAX_VALUE)
				throw new PsiException("rangecheck");
			try
			{
				return new PsiInteger(random.nextInt(numericValue));
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
		return new PsiBoolean(random.nextBoolean());
	}

	public PsiReal psiNormalDeviate(PsiNumeric numeric)
	{
		return new PsiReal(numeric.getValue().doubleValue()*random.nextGaussian());
	}

	private java.util.Random random;
}
