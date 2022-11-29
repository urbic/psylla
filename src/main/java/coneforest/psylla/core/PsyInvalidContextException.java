package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("invalidcontext")
public class PsyInvalidContextException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "invalidcontext";
	}
}
