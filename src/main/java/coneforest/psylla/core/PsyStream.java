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

	public static PsyStream psyIterate(final PsyObject o, final PsyProc oProc, final PsyContext oContext)
	{
		return new PsyStream(java.util.stream.Stream.<PsyObject>iterate(o,
				oProc.asUnaryOperator(oContext)));
	}

	@Override
	public java.util.stream.Stream<? extends PsyObject> stream()
	{
		return stream;
	}

	private final java.util.stream.Stream<? extends PsyObject> stream;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Action
				("iterate",
					(oContext)->
					{
						final var ostack=oContext.operandStackBacked(2);
						ostack.push(PsyStream.psyIterate(ostack.getBacked(0), ostack.getBacked(1), oContext));
					}),
		};
}
