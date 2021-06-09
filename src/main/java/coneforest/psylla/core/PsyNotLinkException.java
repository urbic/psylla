package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("notlink")
public class PsyNotLinkException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "notlink";
	}
}
