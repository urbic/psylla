package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of an {@code object}, a generic Psylla object.
*/
@Type("object")
public interface PsyObject
	extends
		PsyConvertableToName,
		PsyConvertableToString
{
	/**
	*	Returns a string representation of a type name.
	*
	*	@return a string {@code "object"}.
	*/
	default public String typeName()
	{
		if(getClass().isAnnotationPresent(Type.class))
			return (getClass().getAnnotation(Type.class)).value();
		if(getClass().getSuperclass().isAnnotationPresent(Type.class))
			return (getClass().getSuperclass().getAnnotation(Type.class)).value();
		for(final var iface: getClass().getInterfaces())
			if(iface.isAnnotationPresent(Type.class))
				return (iface.getAnnotation(Type.class)).value();
		return null;
		//return java.lang.invoke.MethodHandles.lookup().lookupClass()
		//	.getAnnotation(Type.class).value();
	}

	default public PsyName psyType()
	{
		return new PsyName(typeName());
	}

	/**
	*	Execute this object in the current context. Pushes this object into
	*	interpreter’s operand stack.
	*
	*	@param oContext
	*/
	default public void execute(final PsyContext oContext)
	{
		oContext.operandStack().push(this);
	}

	/**
	*	Invoke this object in the current context. Pushes this object into
	*	interpreter’s operand stack.
	*
	*	@param oContext
	*/
	default public void invoke(final PsyContext oContext)
	{
		oContext.operandStack().push(this);
	}

	default public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.valueOf(this==o);
	}

	default public PsyBoolean psyNe(final PsyObject o)
	{
		return psyEq(o).psyNot();
	}

	/**
	*	Returns a clone of this object.
	*
	*	@return a clone of this object.
	*/
	default public PsyObject psyClone()
	{
		return this;
	}

	default public PsyName psySyntax()
	{
		return new PsyName(toSyntaxString());
	}

	/**
	*	Returns a {@code string} representing this object.
	*
	*	@return a {@code string} representing this object.
	*/
	@Override
	default public PsyString psyToString()
	{
		return new PsyString(toSyntaxString());
	}

	/**
	*	Returns a {@code name} representing this object.
	*
	*	@return a {@code name} representing this object.
	*/
	@Override
	default public PsyName psyToName()
	{
		return new PsyName(toSyntaxString());
	}

	default public String toSyntaxString()
	{
		return '%'+typeName()+'%';
	}

	// TODO
	default public PsyString convert(final Class<PsyString> clazz)
	{
		return new PsyString(toSyntaxString());
	}

	// TODO
	default public PsyBoolean psyInstanceOf(final PsyTextual oTypeName)
	{
		//Class<? extends PsyObject> clazz=TypeRegistry.get(textual.getString());
		//return PsyBoolean.valueOf(clazz!=null && clazz.isInstance(this));

		//final var interpreter=Interpreter.currentInterpreter();
		return PsyBoolean.TRUE;
	}

	/**
	*	Returns an {@code integer} hash code for this object.
	*
	*	@return an {@code integer} hash code for this object.
	*/
	default public PsyInteger psyHashCode()
	{
		return PsyInteger.valueOf(hashCode());
	}


	//static final PsyNamespace NAMESPACE=PsyNamespace.namespace(PsyObject.class);
	/*
	default public PsyObject psyConvert(PsyName oTypeName)
		throws PsyException
	{
		if(getClass().equals(type.getTypeClass()))
			return this;
		try
		{
			java.lang.invoke.MethodHandle handle
				=java.lang.invoke.MethodHandles
					.lookup()
					.findVirtual(getClass(), "convert",
						java.lang.invoke.MethodType.methodType(type.getTypeClass(), type.getTypeClass().getClass()));
			return (PsyObject)handle.invoke(this, type.getTypeClass());
		}
		catch(final PsyException e)
		{
			throw e;
		}
		catch(final Throwable e)
		{
			System.out.println(e);
			throw new PsyUnregisteredException();
		}
	}
	*/
	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyObject>("clone", PsyObject::psyClone),
			new PsyOperator.Arity21<PsyObject, PsyObject>("eq", PsyObject::psyEq),
			new PsyOperator.Arity11("hashcode", PsyObject::psyHashCode),
			new PsyOperator.Arity21<PsyObject, PsyTextual>("instanceof", PsyObject::psyInstanceOf),
			new PsyOperator.Arity21<PsyObject, PsyObject>("ne", PsyObject::psyNe),
			new PsyOperator.Arity11<PsyObject>("toname", PsyObject::psyToName),
			new PsyOperator.Arity11<PsyObject>("tostring", PsyObject::psyToString),
			new PsyOperator.Arity11<PsyObject>("type", PsyObject::psyType),
		};

}
