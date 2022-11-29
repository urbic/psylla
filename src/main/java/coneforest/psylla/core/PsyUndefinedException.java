package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("undefined")
public class PsyUndefinedException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "undefined";
	}
}
