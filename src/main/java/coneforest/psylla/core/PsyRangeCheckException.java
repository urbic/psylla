package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("rangecheck")
public class PsyRangeCheckException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "rangecheck";
	}
}
