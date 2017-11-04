package coneforest.psylla.core;

public class PsyUnsupportedException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "unsupported";
	}
}
