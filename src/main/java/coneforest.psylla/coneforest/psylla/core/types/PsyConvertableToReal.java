package coneforest.psylla.core.types;

import coneforest.psylla.core.errors.PsyError;

public interface PsyConvertableToReal
	extends PsyObject
{
	public PsyReal psyToReal()
		throws PsyError;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyConvertableToReal>("toreal",
				PsyConvertableToReal::psyToReal),
		};
}
