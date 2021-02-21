package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("sequential")
public interface PsySequential<T extends PsyObject>
	extends PsyObject
{

	public void psyForAll(final PsyObject oProc)
		throws PsyException;
}
