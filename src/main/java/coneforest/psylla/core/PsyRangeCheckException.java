package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("rangecheck")
public class PsyRangeCheckException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "rangecheck";
	}
}
