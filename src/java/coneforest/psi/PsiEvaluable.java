package coneforest.psi;

public interface PsiEvaluable extends PsiObject
{
	public void eval(Interpreter interpreter)
		throws PsiException;
}
