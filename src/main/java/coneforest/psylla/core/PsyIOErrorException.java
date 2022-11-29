package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("ioerror")
public class PsyIOErrorException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "ioerror";
	}
}
