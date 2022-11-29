package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("notlink")
public class PsyNotLinkException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "notlink";
	}
}
