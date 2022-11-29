package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("invalidexternal")
public class PsyInvalidExternalException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "invalidexternal";
	}
}
