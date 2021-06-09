package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("streamable")
public interface PsyStreamable<T extends PsyObject>
	extends PsySequential<T>
{

	@Override
	default public void psyForAll(final PsyObject oProc)
		throws PsyException
	{
		psyStream().psyForAll(oProc);
	}

	public PsyFormalStream<T> psyStream();

}
