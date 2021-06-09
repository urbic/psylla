package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("filenotfound")
public class PsyFileNotFoundException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "filenotfound";
	}
}
