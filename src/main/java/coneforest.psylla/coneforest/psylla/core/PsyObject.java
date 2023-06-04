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
	*	@return TODO a string {@code "object"}.
	*/
	default public String typeName()
	{
		var agenda=new java.util.Stack<Class<?>>();
		agenda.push(getClass());

		while(agenda.size()>0)
		{
			var clazz=agenda.pop();
			if(clazz.isAnnotationPresent(Type.class))
				return (clazz.getAnnotation(Type.class)).value();
			agenda.push(clazz.getSuperclass());
			for(final var iface: clazz.getInterfaces())
				agenda.push(iface);
		}
		return null;
	}

	default public PsyName psyType()
	{
		return new PsyName(typeName());
	}

	/**
	*	Execute this object in the given execution context. Pushes this object
	*	into context’s operand stack.
	*
	*	@param oContext the execution context.
	*/
	default public void execute(final PsyContext oContext)
	{
		oContext.operandStack().push(this);
	}

	/**
	*	Invoke this object in the given execution context. Pushes this object
	*	into context’s operand stack.
	*
	*	@param oContext the execution context.
	*/
	default public void invoke(final PsyContext oContext)
	{
		oContext.operandStack().push(this);
	}

	default public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.of(this==o);
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
		//return PsyBoolean.of(clazz!=null && clazz.isInstance(this));

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
		return PsyInteger.of(hashCode());
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
		catch(final Throwable ex)
		{
			System.out.println(ex);
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