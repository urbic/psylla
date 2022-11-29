package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("invalidregexp")
public class PsyInvalidRegExpException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "invalidregexp";
	}
}
