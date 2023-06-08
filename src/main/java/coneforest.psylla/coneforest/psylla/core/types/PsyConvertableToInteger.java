package coneforest.psylla.core.types;

import coneforest.psylla.core.errors.PsyError;

public interface PsyConvertableToInteger
	extends PsyObject
{
	public PsyInteger psyToInteger()
		throws PsyError;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyConvertableToInteger>("tointeger",
				PsyConvertableToInteger::psyToInteger),
		};
}
