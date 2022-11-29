package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("interrupt")
public class PsyInterruptException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "interrupt";
	}
}
