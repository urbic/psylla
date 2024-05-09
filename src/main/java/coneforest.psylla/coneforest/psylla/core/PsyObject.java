package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of an {@code object}, a generic Psylla object.
*/
@Type("object")
public interface PsyObject
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
	*	Execute this object in the given execution context. Pushes this object into context’s
	*	operand stack.
	*
	*	@param oContext the execution context.
	*/
	default public void execute(final PsyContext oContext)
	{
		oContext.operandStack().push(this);
	}

	/**
	*	Invoke this object in the given execution context. Pushes this object into context’s operand
	*	stack.
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
	default public PsyString psyToString()
	{
		return new PsyString(toSyntaxString());
	}

	/**
	*	Returns a {@code name} representing this object.
	*
	*	@return a {@code name} representing this object.
	*/
	default public PsyName psyToName()
	{
		return new PsyName(toSyntaxString());
	}

	/**
	*	Returns the syntactic representation of this object.
	*
	*	@return the syntactic representation of this object.
	*/
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

	/**
	*	Context action of the {@code clone} operator.
	*/
	@OperatorType("clone")
	public static final ContextAction PSY_CLONE
		=ContextAction.<PsyObject>ofFunction(PsyObject::psyClone);

	/**
	*	Context action of the {@code eq} operator.
	*/
	@OperatorType("eq")
	public static final ContextAction PSY_EQ
		=ContextAction.<PsyObject, PsyObject>ofBiFunction(PsyObject::psyEq);

	/**
	*	Context action of the {@code hashcode} operator.
	*/
	@OperatorType("hashcode")
	public static final ContextAction PSY_HASHCODE
		=ContextAction.<PsyObject>ofFunction(PsyObject::psyHashCode);

	/**
	*	Context action of the {@code instanceof} operator.
	*/
	@OperatorType("instanceof")
	public static final ContextAction PSY_INSTANCEOF
		=ContextAction.<PsyObject, PsyTextual>ofBiFunction(PsyObject::psyInstanceOf);

	/**
	*	Context action of the {@code ne} operator.
	*/
	@OperatorType("ne")
	public static final ContextAction PSY_NE
		=ContextAction.<PsyObject, PsyObject>ofBiFunction(PsyObject::psyNe);

	/**
	*	Context action of the {@code toname} operator.
	*/
	@OperatorType("toname")
	public static final ContextAction PSY_TONAME
		=ContextAction.<PsyObject>ofFunction(PsyObject::psyToName);

	/**
	*	Context action of the {@code tostring} operator.
	*/
	@OperatorType("tostring")
	public static final ContextAction PSY_TOSTRING
		=ContextAction.<PsyObject>ofFunction(PsyObject::psyToString);

	/**
	*	Context action of the {@code type} operator.
	*/
	@OperatorType("type")
	public static final ContextAction PSY_TYPE
		=ContextAction.<PsyObject>ofFunction(PsyObject::psyType);
}
