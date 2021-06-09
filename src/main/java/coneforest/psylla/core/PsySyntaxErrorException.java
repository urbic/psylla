package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("syntaxerror")
public class PsySyntaxErrorException
	extends PsyException
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
