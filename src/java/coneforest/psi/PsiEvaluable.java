package coneforest.psi;

/**
 *	A type of Ψ objects that can be evaluated.
 */
public interface PsiEvaluable extends PsiObject
{
	/**
	 *	Evaluate this object in the context of interpreter.
	 *
	 *	@param interpreter an interpreter.
	 */
	public void eval(Interpreter interpreter)
		throws PsiException;
}
