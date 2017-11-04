package coneforest.psylla.core;

public class PsyConcurrentModificationException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "concurrentmodification";
	}
}
