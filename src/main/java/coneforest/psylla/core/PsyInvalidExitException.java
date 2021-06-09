package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("invalidexit")
public class PsyInvalidExitException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "invalidexit";
	}
}
