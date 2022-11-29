package coneforest.psylla.core;

public interface PsyConvertableToIntegral
	extends PsyObject
{
	public PsyIntegral psyToIntegral()
		throws PsyErrorException;
}
