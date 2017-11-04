package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("module")
public class PsyModule
	extends PsyDict
{

	protected void registerOperators(final PsyOperator... operators)
	{
		for(final PsyOperator oOperator: operators)
			put(oOperator.getName(), oOperator);
	}
}
