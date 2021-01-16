package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("range")
public class PsyRange
	implements PsyStreamlike<PsyRealNumeric>
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
	public java.util.Iterator<PsyRealNumeric> iterator()
	{
		final boolean forward=oIncrement.psyGt(PsyInteger.ZERO).booleanValue();
		return new java.util.Iterator<PsyRealNumeric>()
			{
				@Override
				public boolean hasNext()
				{
					return (forward? oInitial.psyLe(oLimit): oInitial.psyGe(oLimit)).booleanValue();
				}

				@Override
				public PsyRealNumeric next()
				{
					final PsyRealNumeric oCurrent=oInitial;
					oInitial=(PsyRealNumeric)oInitial.psyAdd(oIncrement);
					return oCurrent;
				}
			};
	}

	private PsyRealNumeric oInitial;
	private final PsyRealNumeric oIncrement, oLimit;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity31<PsyRealNumeric, PsyRealNumeric, PsyRealNumeric>("range", PsyRange::new),
		};

}
