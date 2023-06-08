package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import java.io.IOException;
import java.nio.CharBuffer;

@Type("ready")
public interface PsyReady
	extends PsyObject
{
	public PsyBoolean psyReady()
		throws PsyError;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyReady>("ready", PsyReady::psyReady),
		};
}
