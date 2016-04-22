package coneforest.psi.core;
import coneforest.psi.*;

/**
*	A representation of Ψ-{@code evaluable}, a type of objects that can be
*	interpreted as the program.
*/
public interface PsiEvaluable
	extends PsiObject
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
	*	Evaluate this object in the context of given interpreter.
	*
	*	@param interpreter an interpreter.
	*	@throws PsiException when an error occurs durind evaluation of this
	*	object.
	*/
	public void eval(Interpreter interpreter)
		throws PsiException;
}
