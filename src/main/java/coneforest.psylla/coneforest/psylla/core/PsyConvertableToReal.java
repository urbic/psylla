package coneforest.psylla.core;

import coneforest.psylla.*;

public interface PsyConvertableToReal
	extends PsyObject
{
	public PsyReal psyToReal()
		throws PsyErrorException;

	/**
	*	Context action of the {@code toreal} operator.
	*/
	@OperatorType("toreal")
	public static final ContextAction PSY_TOREAL
		=ContextAction.<PsyConvertableToReal>ofFunction(PsyConvertableToReal::psyToReal);
}
