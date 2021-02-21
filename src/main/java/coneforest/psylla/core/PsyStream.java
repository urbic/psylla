package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("stream")
public class PsyStream
	implements PsyStreamlike
{
	public PsyStream(java.util.stream.Stream<? extends PsyObject> stream)
	{
		this.stream=stream;
	}

	public static PsyStream psyIterate(final PsyObject o, final PsyProc oProc)
	{
		return new PsyStream(java.util.stream.Stream.<PsyObject>iterate(o,
				oProc.asUnaryOperator()));
	}

	@Override
	public java.util.stream.Stream<? extends PsyObject> stream()
	{
		return stream;
	}

	private final java.util.stream.Stream<? extends PsyObject> stream;

}
