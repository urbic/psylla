package coneforest.psylla.core;

import coneforest.psylla.*;
import java.io.IOException;
import java.nio.CharBuffer;

/**
*	The representation of {@code ready}, an object that can report its readiness for something.
*/
@Type("ready")
public interface PsyReady
	extends PsyObject
{
	public PsyBoolean psyReady()
		throws PsyErrorException;

	/**
	*	Context action of the {@code ready} operator.
	*/
	@OperatorType("ready")
	public static final ContextAction PSY_READY
		=ContextAction.<PsyReady>ofFunction(PsyReady::psyReady);
}
