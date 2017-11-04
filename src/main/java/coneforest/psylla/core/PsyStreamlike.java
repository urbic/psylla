package coneforest.psylla.core;

public interface PsyStreamlike<T extends PsyObject>
	extends PsyObject
{
	public PsyInteger psyCount();

	public PsyStreamlike<T> psyConcat(PsyStreamlike<T> streamlike);
}
