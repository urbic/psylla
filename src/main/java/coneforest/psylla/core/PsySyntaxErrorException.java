package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("syntaxerror")
public class PsySyntaxErrorException
	extends PsyErrorException
{
	public PsySyntaxErrorException()
	{
		super();
	}

	public PsySyntaxErrorException(final PsyObject oEmitter)
	{
		super(oEmitter);
	}

	@Override
	public String getName()
	{
		return "syntaxerror";
	}
}
