package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code sequential}.
*
*	@param <T> the type of the objects in the sequence.
*/
@Type("sequential")
public interface PsySequential<T extends PsyObject>
	extends PsyObject
{
	public void psyForAll(final PsyObject oProc, final PsyContext oContext)
		throws PsyErrorException;

	/**
	*	Context action of the {@code forall} operator.
	*/
	@OperatorType("forall")
	public static final ContextAction PSY_FORALL=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			ostack.<PsySequential>getBacked(0).psyForAll(ostack.getBacked(1), oContext);
		};
}
