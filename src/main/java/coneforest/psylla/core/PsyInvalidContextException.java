package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("invalidcontext")
public class PsyInvalidContextException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "invalidcontext";
	}
}
