package coneforest.psylla.core;

import coneforest.psylla.*;

@Type("module")
public class PsyModule
	extends PsyDict
{
	protected void registerOperators(final PsyOperator... operators)
	{
		for(final var oOperator: operators)
			put(oOperator.getName(), oOperator);
	}

	protected void importOperators(final Class<?>... classes)
	{
		try
		{
			for(final var clz: classes)
				for(final var field: clz.getFields())
				{
					final var annotation=field.getAnnotation(OperatorType.class);
					if(annotation!=null)
					{
						final var operatorName=annotation.value();
						final var action=(ContextAction)field.get(null);
						put(operatorName,
							new PsyOperator(operatorName)
								{
									public void perform(final PsyContext oContext)
										throws ClassCastException, PsyErrorException
									{
										action.perform(oContext);
									}								
								});
					}
				}
		}
		catch(final IllegalAccessException ex)
		{
			// TODO: more appropriate exception
		}
	}
}
