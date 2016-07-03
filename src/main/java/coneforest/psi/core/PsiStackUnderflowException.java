package coneforest.psi.core;

public class PsiStackUnderflowException
	extends PsiException
{
	@Override
	public String getName()
	{
		return "stackunderflow";
	}
}
