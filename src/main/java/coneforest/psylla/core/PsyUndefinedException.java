package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("undefined")
public class PsyUndefinedException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "undefined";
	}
}
