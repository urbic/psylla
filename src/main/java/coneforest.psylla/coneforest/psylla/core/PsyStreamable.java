package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code streamable}, an object that provide stream of something.
*
*	@param <T> the type of the elements of the stream.
*/
@Type("streamable")
public interface PsyStreamable<T extends PsyObject>
	extends PsySequential<T>
{

	@Override
	default public void psyForAll(final PsyObject oProc, final PsyContext oContext)
		throws PsyErrorException
	{
		psyStream().psyForAll(oProc, oContext);
	}

	public PsyFormalStream<T> psyStream();

	/**
	*	Context action of the {@code stream} operator.
	*/
	@OperatorType("stream")
	public static final ContextAction PSY_STREAM
		=ContextAction.<PsyStreamable>ofFunction(PsyStreamable::psyStream);
}
