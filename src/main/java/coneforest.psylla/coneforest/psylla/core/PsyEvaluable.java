package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code evaluable}, an object that can be interpreted as the program.
*/
@Type("evaluable")
public interface PsyEvaluable
	extends PsyObject
{
	/**
	*	Context action of the {@code eval} operator.
	*/
	@OperatorType("eval")
	public static final ContextAction PSY_EVAL=oContext->
		oContext.operandStackBacked(1).<PsyEvaluable>getBacked(0).psyEval(oContext);

	/**
	*	Evaluate this object in the current context.
	*
	*	@throws PsyErrorException when an error occurs durind evaluation of this object.
	*/
	public void psyEval(final PsyContext oContext)
		throws PsyErrorException;
}
