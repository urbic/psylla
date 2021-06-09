package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("stream")
public class PsyStream
	implements PsyFormalStream
{
	public PsyStream(java.util.stream.Stream<? extends PsyObject> stream)
	{
		this.stream=stream;
	}

	/*XXXpublic static PsyStream psyIterate(final PsyObject o, final PsyProc oProc)
	{
		return new PsyStream(java.util.stream.Stream.<PsyObject>iterate(o,
				oProc.asUnaryOperator()));
	}*/

	@Override
	public java.util.stream.Stream<? extends PsyObject> stream()
	{
		return stream;
	}

	private final java.util.stream.Stream<? extends PsyObject> stream;

}
