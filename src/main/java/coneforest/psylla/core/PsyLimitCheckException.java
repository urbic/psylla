package coneforest.psylla.core;

public class PsyLimitCheckException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "limitcheck";
	}
}
