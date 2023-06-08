package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;

@Type("streamable")
public interface PsyStreamable<T extends PsyObject>
	extends PsySequential<T>
{

	@Override
	default public void psyForAll(final PsyObject oProc, final PsyContext oContext)
		throws PsyError
	{
		psyStream().psyForAll(oProc, oContext);
	}

	public PsyFormalStream<T> psyStream();

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyStreamable>
				("stream", PsyStreamable::psyStream),
		};

}
