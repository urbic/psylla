package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("exception")
abstract public class PsyException
	extends	Exception
	implements PsyObject
{
	public PsyException()
	{
	}

	public PsyException(final PsyObject oEmitter)
	{
		setEmitter(oEmitter);
	}

	public String getName()
	{
		return "exception";
	};

	public void setEmitter(final PsyObject oEmitter)
	{
		this.emitter=oEmitter;
	}

	public PsyObject getEmitter()
	{
		return emitter;
	}

	private PsyObject emitter;
}
