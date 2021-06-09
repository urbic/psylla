package coneforest.psylla;

//@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE,java.lang.annotation.ElementType.TYPE_USE})
@java.lang.annotation.Target(java.lang.annotation.ElementType.TYPE)
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
//@java.lang.annotation.Inherited
public @interface ExceptionType
{
	String value();
}
