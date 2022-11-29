package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("invalidexit")
public class PsyInvalidExitException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "invalidexit";
	}
}
