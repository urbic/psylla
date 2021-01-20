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

	@Override
	public java.util.Iterator iterator()
	{
		return stream.iterator();
	}

	public static PsyStream psyIterate(final PsyObject o, final PsyProc oProc)
	{
		//return java.util.stream.Stream<? extends PsyObject>.<PsyObject>iterate(o, oProc.asUnaryOperator()).
		return new PsyStream(java.util.stream.Stream.<PsyObject>iterate(o,
				oProc.asUnaryOperator()));
	}

	private final java.util.stream.Stream<? extends PsyObject> stream;

}
