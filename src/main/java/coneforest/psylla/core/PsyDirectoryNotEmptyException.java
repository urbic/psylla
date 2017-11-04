package coneforest.psylla.core;

public class PsyDirectoryNotEmptyException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "directorynotempty";
	}
}
