package coneforest.psylla.runtime;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
*	Annotates the Psylla types attaching the name to the type.
*/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Type
{
	/**
	*	{@return the name under which the type is visible to the Psylla interpreter}
	*/
	public String value();
}
