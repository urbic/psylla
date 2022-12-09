package coneforest.psylla.core;

public interface PsyConvertableToIntegral
	extends PsyObject
{
	public PsyIntegral psyToIntegral()
		throws PsyErrorException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyConvertableToIntegral>("tointegral",
				PsyConvertableToIntegral::psyToIntegral),
		};
}
