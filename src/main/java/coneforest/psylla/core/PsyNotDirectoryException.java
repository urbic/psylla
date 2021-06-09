package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("notdirectory")
public class PsyNotDirectoryException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "notdirectory";
	}
}
