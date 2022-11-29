package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("unsupported")
public class PsyUnsupportedException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "unsupported";
	}
}
