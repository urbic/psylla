package coneforest.psylla.core;

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
