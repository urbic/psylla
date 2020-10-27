package coneforest.psylla;
import coneforest.psylla.core.PsyOperator;

@java.lang.annotation.Target(java.lang.annotation.ElementType.METHOD)
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Operator
{
	String name();
	Class<? extends PsyOperator> type();

	final Class<PsyOperator.Arity21> ARITY_21=PsyOperator.Arity21.class;
	/*public enum Type
	{
		ARITY_21(PsyOperator.Arity21.class);

		Type(final Class<? extends PsyOperator> op)
		{
		}
	}*/
}

