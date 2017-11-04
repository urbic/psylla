package coneforest.psylla.core;

public class PsyFileAccessDeniedException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "fileaccessdenied";
	}
}
