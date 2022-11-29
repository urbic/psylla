package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("invalidstate")
public class PsyInvalidStateException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "invalidstate";
	}
}
