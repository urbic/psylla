package coneforest.psylla.core;

public interface PsyConvertableToInteger
	extends PsyObject
{
	public PsyInteger psyToInteger()
		throws PsyErrorException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyConvertableToInteger>("tointeger",
				PsyConvertableToInteger::psyToInteger),
		};
}
