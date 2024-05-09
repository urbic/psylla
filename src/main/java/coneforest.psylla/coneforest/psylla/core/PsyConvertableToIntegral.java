package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@Type("convertabletointegral")
public interface PsyConvertableToIntegral
	extends PsyObject
{
	public PsyIntegral psyToIntegral()
		throws PsyErrorException;

	/**
	*	Context action of the {@code tointegral} operator.
	*/
	@OperatorType("tointegral")
	public static final ContextAction PSY_TOINTEGRAL
		=ContextAction.<PsyConvertableToIntegral>ofFunction(PsyConvertableToIntegral::psyToIntegral);
}
