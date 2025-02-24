package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code ready}, an object that can report its readiness for something.
*/
@Type("ready")
public interface PsyReady
	extends PsyObject
{
	/**
	*	Context action of the {@code ready} operator.
	*/
	@OperatorType("ready")
	public static final ContextAction PSY_READY
		=ContextAction.<PsyReady>ofFunction(PsyReady::psyReady);

	public PsyBoolean psyReady()
		throws PsyErrorException;
}
