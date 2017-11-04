package coneforest.psylla.core;

public interface PsyConvertableToInteger
	extends PsyObject
{
	public PsyInteger psyToInteger()
		throws PsyException;
}
