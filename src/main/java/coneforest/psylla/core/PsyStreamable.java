package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("streamable")
public interface PsyStreamable<T extends PsyObject>
	extends PsySequential
{

	@Override
	default public void psyForAll(final PsyObject oProc)
		throws PsyException
	{
		psyStream().psyForAll(oProc);
	}

	public PsyStreamlike<T> psyStream();

}
