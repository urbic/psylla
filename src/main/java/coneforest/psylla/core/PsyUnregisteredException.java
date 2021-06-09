package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("unregistered")
public class PsyUnregisteredException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "unregistered";
	}
}
