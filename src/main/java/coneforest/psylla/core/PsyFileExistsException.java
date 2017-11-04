package coneforest.psylla.core;

public class PsyFileExistsException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "fileexists";
	}
}
