package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("notimplemented")
public class PsyNotImplementedException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "notimplemented";
	}
}
