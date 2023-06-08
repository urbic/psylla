package coneforest.psylla.core.types;

import coneforest.psylla.core.errors.PsyError;

public interface PsyConvertableToIntegral
	extends PsyObject
{
	public PsyIntegral psyToIntegral()
		throws PsyError;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyConvertableToIntegral>("tointegral",
				PsyConvertableToIntegral::psyToIntegral),
		};
}
