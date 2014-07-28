package coneforest.psi;

public class PsiRandom
	extends PsiObject
{
	public PsiRandom()
	{
		random=new java.util.Random();
	}

	public void setSeed(PsiInteger seed)
	{
		random.setSeed(seed.getValue());
	}

	private java.util.Random random;
}
