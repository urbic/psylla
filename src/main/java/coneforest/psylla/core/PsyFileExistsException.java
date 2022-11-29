package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("fileexists")
public class PsyFileExistsException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "fileexists";
	}
}
