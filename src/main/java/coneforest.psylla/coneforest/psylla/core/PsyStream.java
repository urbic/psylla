package coneforest.psylla.core;

import coneforest.psylla.*;
import java.util.stream.Stream;

/**
*	The representation of {@code stream}.
*/
@Type("stream")
public class PsyStream
	implements PsyFormalStream
{
	protected PsyStream(Stream<? extends PsyObject> stream)
	{
		this.stream=stream;
	}

	public static PsyStream psyIterate(final PsyObject o, final PsyExecutable oUnaryOp, final PsyContext oContext)
	{
		return new PsyStream(Stream.<PsyObject>iterate(o, oUnaryOp.asUnaryOperator(oContext)));
	}

	@Override
	public Stream<? extends PsyObject> stream()
	{
		return stream;
	}

	private final Stream<? extends PsyObject> stream;

	/**
	*	Context action of the {@code iterate} operator.
	*/
	@OperatorType("iterate")
	public static final ContextAction PSY_ITERATE=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			ostack.push(PsyStream.psyIterate(ostack.getBacked(0), ostack.getBacked(1), oContext));
		};

	/**
	*	Context action of the {@code streamtomark} operator.
	*/
	@OperatorType("streamtomark")
	public static final ContextAction PSY_STREAMTOMARK=oContext->
		{
			final var ostack=oContext.operandStack();
			final var i=ostack.findMarkPosition()+1;
			final var s=ostack.size();
			final var objects=new PsyObject[s-i];
			for(int j=i; j<s; j++)
				objects[j-i]=ostack.get(j);
			ostack.setSize(i-1);
			ostack.push(new PsyStream(Stream.of(objects)));
		};
}
