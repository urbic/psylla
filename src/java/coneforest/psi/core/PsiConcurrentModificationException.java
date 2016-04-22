package coneforest.psi.core;

public class PsiConcurrentModificationException
	extends PsiException
{
	@Override
	public String getName()
	{
		return "concurrentmodification";
	}
}
