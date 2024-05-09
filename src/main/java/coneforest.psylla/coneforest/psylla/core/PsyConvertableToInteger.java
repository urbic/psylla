package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@Type("convertabletointeger")
public interface PsyConvertableToInteger
	extends PsyObject
{
	public PsyInteger psyToInteger()
		throws PsyErrorException;

	/**
	*	Context action of the {@code tointeger} operator.
	*/
	@OperatorType("tointeger")
	public static final ContextAction PSY_TOINTEGER
		=ContextAction.<PsyConvertableToInteger>ofFunction(PsyConvertableToInteger::psyToInteger);
}
