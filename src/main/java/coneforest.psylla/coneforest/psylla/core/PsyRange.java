package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
*	The representation of {@code range}.
*/
@Type("range")
public class PsyRange
	implements PsyFormalStream<PsyRealNumeric>
{

	public PsyRange(final PsyRealNumeric oInitial,
			final PsyRealNumeric oIncrement,
			final PsyRealNumeric oLimit)
	{
		this.oInitial=oInitial;
		this.oIncrement=oIncrement;
		this.oLimit=oLimit;
	}

	@Override
	public Stream<PsyRealNumeric> stream()
	{
		final boolean forward=(oIncrement.compareTo(PsyInteger.ZERO)>0);
		return StreamSupport.<PsyRealNumeric>stream(
				Spliterators.<PsyRealNumeric>spliteratorUnknownSize(
					new Iterator<PsyRealNumeric>()
						{
							@Override
							public boolean hasNext()
							{
								return forward?
										oNext.compareTo(oLimit)<=0:
										oNext.compareTo(oLimit)>=0;
							}

							@Override
							public PsyRealNumeric next()
							{
								oCurrent=oNext;
								oNext=(PsyRealNumeric)oCurrent.psyAdd(oIncrement);
								return oCurrent;
							}

							private PsyRealNumeric oCurrent;
							private PsyRealNumeric oNext=oInitial;
						}, 0),
					false);
	}

	@Override
	public String toSyntaxString()
	{
		return '%'+typeName()
			+'='+oInitial.toSyntaxString()
			+';'+oIncrement.toSyntaxString()
			+';'+oLimit.toSyntaxString()
			+'%';
	}

	private final PsyRealNumeric oInitial, oIncrement, oLimit;

	/**
	*	Context action of the {@code range} operator.
	*/
	@OperatorType("range")
	public static final ContextAction PSY_RANGE
		=ContextAction.<PsyRealNumeric, PsyRealNumeric, PsyRealNumeric>ofTriFunction(PsyRange::new);
}
