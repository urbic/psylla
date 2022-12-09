package coneforest.psylla.core;

public interface PsyConvertableToReal
	extends PsyObject
{
	public PsyReal psyToReal()
		throws PsyErrorException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyConvertableToReal>("toreal",
				PsyConvertableToReal::psyToReal),
		};
}
