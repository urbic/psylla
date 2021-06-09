package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("interrupt")
public class PsyInterruptException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "interrupt";
	}
}
