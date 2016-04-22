package coneforest.psi.core;

public interface PsiBounded
	extends
		PsiLengthy
{
	@Override
	default public String getTypeName()
	{
		return "bounded";
	}

	public int capacity();

	default public PsiInteger psiCapacity()
	{
		return PsiInteger.valueOf(capacity());
	}

	default public boolean isFull()
	{
		return length()==capacity();
	}

	default public PsiBoolean psiIsFull()
	{
		return PsiBoolean.valueOf(isFull());
	}
}
