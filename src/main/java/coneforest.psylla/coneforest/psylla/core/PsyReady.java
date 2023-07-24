package coneforest.psylla.core;

import coneforest.psylla.*;
import java.io.IOException;
import java.nio.CharBuffer;

/**
*	A representation of {@code ready}.
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
