package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("concurrentmodification")
public class PsyConcurrentModificationException
	extends PsyErrorException
{
	@Override
	public String getName()
	{
		return "concurrentmodification";
	}
}
