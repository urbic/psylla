package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("unmatchedmark")
public class PsyUnmatchedMarkException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "unmatchedmark";
	}
}
