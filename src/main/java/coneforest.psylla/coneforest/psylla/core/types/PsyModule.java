package coneforest.psylla.core.types;

import coneforest.psylla.Type;

@Type("module")
public class PsyModule
	extends PsyDict
{
	protected void registerOperators(final PsyOperator... operators)
	{
		for(final var oOperator: operators)
			put(oOperator.getName(), oOperator);
	}
}
