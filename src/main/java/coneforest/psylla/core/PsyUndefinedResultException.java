package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("undefinedresult")
public class PsyUndefinedResultException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "undefinedresult";
	}
}
