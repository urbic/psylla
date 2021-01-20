package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code evaluable}, a type of objects that can be
*	interpreted as the program.
*/
@coneforest.psylla.Type("evaluable")
public interface PsyEvaluable
	extends PsyObject
{

	/**
	*	Evaluate this object in the context of given interpreter.
	*
	*	@param interpreter an interpreter.
	*	@throws PsyException when an error occurs durind evaluation of this
	*	object.
	*/
	public void psyEval()
		throws PsyException;

}
