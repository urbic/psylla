package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("typecheck")
public class PsyTypeCheckException
	extends PsyErrorException
{
	public PsyTypeCheckException()
	{
		super();
	}

	public PsyTypeCheckException(final PsyObject oEmitter)
	{
		super(oEmitter);
	}

	@Override
	public String getName()
	{
		return "typecheck";
	}
}
