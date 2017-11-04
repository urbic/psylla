package coneforest.psylla.core;

public interface PsyConvertableToReal
	extends PsyObject
{
	public PsyReal psyToReal()
		throws PsyException;
}
