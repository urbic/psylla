package coneforest.psylla;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Target({/*ElementType.TYPE, */ElementType.TYPE_USE})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperatorType
{
	String value();
}
