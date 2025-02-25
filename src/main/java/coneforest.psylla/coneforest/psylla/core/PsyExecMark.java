package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@Type("execmark")
public class PsyExecMark
	implements PsyObject
{
	public static final PsyExecMark LOOP_MARK=new PsyExecMark()
		{
			@Override
			public String toSyntaxString()
			{
				return "%loopmark%";
			}
		};

	public static final PsyExecMark STOP_MARK=new PsyExecMark()
		{
			@Override
			public String toSyntaxString()
			{
				return "%stopmark%";
			}
		};

	private PsyExecMark()
	{
		// NOP
	}

	@Override
	public void execute(final PsyContext oContext)
	{
		// NOP
	}

	@Override
	public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.of(this==o);
	}
}
