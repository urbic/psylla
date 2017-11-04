package coneforest.psylla.core;

@coneforest.psylla.Type("exception")
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

	abstract public String getName();

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
