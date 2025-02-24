package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@Type("convertabletorational")
public interface PsyConvertableToRational
	extends PsyObject
{
	/**
	*	Context action of the {@code torational} operator.
	*/
	@OperatorType("torational")
	public static final ContextAction PSY_TORATIONAL
		=ContextAction.<PsyConvertableToRational>ofFunction(PsyConvertableToRational::psyToRational);

	public PsyRational psyToRational()
		throws PsyErrorException;
}
