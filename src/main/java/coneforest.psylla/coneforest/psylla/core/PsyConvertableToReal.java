package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

public interface PsyConvertableToReal
	extends PsyObject
{
	/**
	*	Context action of the {@code toreal} operator.
	*/
	@OperatorType("toreal")
	public static final ContextAction PSY_TOREAL
		=ContextAction.<PsyConvertableToReal>ofFunction(PsyConvertableToReal::psyToReal);

	public PsyReal psyToReal()
		throws PsyErrorException;
}
