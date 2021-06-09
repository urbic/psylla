package coneforest.psylla.core;
import coneforest.psylla.*;

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
	public java.util.stream.Stream<PsyRealNumeric> stream()
	{
		final boolean forward=oIncrement.psyGt(PsyInteger.ZERO).booleanValue();
		return java.util.stream.StreamSupport.<PsyRealNumeric>stream(
				java.util.Spliterators.<PsyRealNumeric>spliteratorUnknownSize(
						new java.util.Iterator<PsyRealNumeric>()
							{
								@Override
								public boolean hasNext()
								{
									return (forward? oNext.psyLe(oLimit): oNext.psyGe(oLimit)).booleanValue();
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

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity31<PsyRealNumeric, PsyRealNumeric, PsyRealNumeric>
				("range", PsyRange::new),
		};

}
