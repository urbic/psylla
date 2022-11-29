package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("unregistered")
public class PsyUnregisteredException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "unregistered";
	}
}
