package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code evaluable}, a type of objects that can be
*	interpreted as the program.
*/
@Type("evaluable")
public interface PsyEvaluable
	extends PsyObject
{

	/**
	*	Evaluate this object in the current context.
	*
	*	@throws PsyException when an error occurs durind evaluation of this
	*	object.
	*/
	public void psyEval()
		throws PsyException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity10<PsyEvaluable>
				("eval", PsyEvaluable::psyEval),
		};

}
