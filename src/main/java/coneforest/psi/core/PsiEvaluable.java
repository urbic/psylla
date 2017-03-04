package coneforest.psi.core;
import coneforest.psi.*;

/**
*	A representation of Ψ-{@code evaluable}, a type of objects that can be
*	interpreted as the program.
*/
@coneforest.psi.Type("evaluable")
public interface PsiEvaluable
	extends PsiObject
{

	/**
	*	Evaluate this object in the context of given interpreter.
	*
	*	@param interpreter an interpreter.
	*	@throws PsiException when an error occurs durind evaluation of this
	*	object.
	*/
	public void eval(final Interpreter interpreter)
		throws PsiException;
}
