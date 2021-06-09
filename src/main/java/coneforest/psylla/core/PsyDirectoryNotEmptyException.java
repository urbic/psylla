package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("directorynotempty")
public class PsyDirectoryNotEmptyException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "directorynotempty";
	}
}
