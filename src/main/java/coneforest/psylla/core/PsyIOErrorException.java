package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("ioerror")
public class PsyIOErrorException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "ioerror";
	}
}
