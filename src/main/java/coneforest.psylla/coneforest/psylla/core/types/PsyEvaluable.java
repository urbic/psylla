package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;

/**
*	A representation of {@code evaluable}, a type of objects that can be
*	interpreted as the program.
*/
@Type("evaluable")
public interface PsyEvaluable
	extends PsyObject
{

	/**
	*	Evaluate this object in the current context.
	*
	*	@throws PsyError when an error occurs durind evaluation of this object.
	*/
	public void psyEval()
		throws PsyError;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity10<PsyEvaluable>
				("eval", PsyEvaluable::psyEval),
		};

}
