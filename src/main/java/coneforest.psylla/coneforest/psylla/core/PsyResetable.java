package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code resetable}, an object that can be reset in some manner.
*/
@Type("resetable")
public interface PsyResetable
	extends PsyObject
{

	/**
	*	Reset this {@code resetable} object.
	*
	*	@throws PsyErrorException when error occured during reset.
	*/
	public void psyReset()
		throws PsyErrorException;

	/**
	*	Context action of the {@code reset} operator.
	*/
	@OperatorType("reset")
	public static final ContextAction PSY_RESET
		=ContextAction.<PsyResetable>ofConsumer(PsyResetable::psyReset);
}
