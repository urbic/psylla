package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("invalidregexp")
public class PsyInvalidRegExpException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "invalidregexp";
	}
}
