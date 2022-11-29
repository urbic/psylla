package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("notdirectory")
public class PsyNotDirectoryException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "notdirectory";
	}
}
