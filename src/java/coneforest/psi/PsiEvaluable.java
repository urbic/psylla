package coneforest.psi;

/**
 *	A type of Ψ objects that can be evaluated.
 */
public interface PsiEvaluable extends PsiObject
{
	/**
	 *	@return a string {@code "evaluable"}.
	 */
	@Override
	default public String getTypeName()
	{
		return "evaluable";
	}

	/**
	 *	Evaluate this object in the context of interpreter.
	 *
	 *	@param interpreter an interpreter.
	 *	@throws PsiException when an error occurs durind evaluation of this
	 *	object.
	 */
	public void eval(Interpreter interpreter)
		throws PsiException;
}
