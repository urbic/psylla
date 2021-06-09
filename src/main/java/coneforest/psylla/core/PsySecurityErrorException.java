package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("securityerror")
public class PsySecurityErrorException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "securityerror";
	}
}
