package coneforest.psylla.core;

import coneforest.psylla.*;
import java.io.IOException;
import java.nio.CharBuffer;

@coneforest.psylla.Type("ready")
public interface PsyReady
	extends PsyObject
{
	public PsyBoolean psyReady()
		throws PsyException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyReady>("ready", PsyReady::psyReady),
		};
}
