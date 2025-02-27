package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.Stack;

/**
*	The representation of an {@code object}, a generic Psylla object.
*/
@Type("object")
public interface PsyObject
{
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
	*	Context action of the {@code tostring} operator.
	*/
	@OperatorType("tostring")
	public static final ContextAction PSY_TOSTRING
		=ContextAction.<PsyObject>ofFunction(PsyObject::psyToString);

	/**
	*	Context action of the {@code tostringbuffer} operator.
	*/
	@OperatorType("tostringbuffer")
	public static final ContextAction PSY_TOSTRINGBUFFER
		=ContextAction.<PsyObject>ofFunction(PsyObject::psyToStringBuffer);

	/**
	*	Context action of the {@code type} operator.
	*/
	@OperatorType("type")
	public static final ContextAction PSY_TYPE
		=ContextAction.<PsyObject>ofFunction(PsyObject::psyType);

	/**
	*	{@return a string representation of this {@code object}’s type name}
	*/
	public default String typeName()
	{
		final var agenda=new Stack<Class<?>>();
		agenda.push(getClass());

		while(agenda.size()>0)
		{
			final var clazz=agenda.pop();
			if(clazz.isAnnotationPresent(Type.class))
				return (clazz.getAnnotation(Type.class)).value();
			agenda.push(clazz.getSuperclass());
			for(final var iface: clazz.getInterfaces())
				agenda.push(iface);
		}
		return null;
	}

	public default PsyString psyType()
	{
		return new PsyString(typeName());
	}

	/**
	*	Execute this {@code object} in the given execution context. Pushes this {@code object} into
	*	context’s operand stack.
	*
	*	@param oContext the execution context.
	*/
	public default void execute(final PsyContext oContext)
	{
		oContext.operandStack().push(this);
	}

	/**
	*	Invoke this {@code object} in the given execution context. Pushes this {@code object} into
	*	context’s operand stack.
	*
	*	@param oContext the execution context.
	*/
	public default void invoke(final PsyContext oContext)
	{
		oContext.operandStack().push(this);
	}

	/**
	*	{@return a {@code boolean} result of equality test of this {@code object} and given {@code
	*	object}}
	*
	*	@param o the {@code object} to be compared for equality.
	*/
	public default PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.of(equals(o));
	}

	public default PsyBoolean psyNe(final PsyObject o)
	{
		return PsyBoolean.of(!equals(o));
	}

	/**
	*	{@return a clone of this object}
	*/
	public default PsyObject psyClone()
	{
		return this;
	}

	/**
	*	{@return a syntactic representation of this object as a {@code name}}
	*/
	public default PsyString psySyntax()
	{
		return new PsyString(toSyntaxString());
	}

	/**
	*	{@return a {@code stringbuffer} representing this object}
	*/
	public default PsyStringBuffer psyToStringBuffer()
	{
		return new PsyStringBuffer(toSyntaxString());
	}

	/**
	*	{@return a {@code string} representing this object}
	*/
	public default PsyString psyToString()
	{
		return new PsyString(toSyntaxString());
	}

	/**
	*	{@return the syntactic representation of this object}
	*/
	public default String toSyntaxString()
	{
		return '%'+typeName()+'%';
	}

	// TODO
	public default PsyStringBuffer convert(final Class<PsyStringBuffer> clazz)
	{
		return new PsyStringBuffer(toSyntaxString());
	}

	// TODO
	public default PsyBoolean psyInstanceOf(final PsyTextual oTypeName)
	{
		//Class<? extends PsyObject> clazz=TypeRegistry.get(textual.getString());
		//return PsyBoolean.of(clazz!=null && clazz.isInstance(this));

		//final var interpreter=Interpreter.currentInterpreter();
		return PsyBoolean.TRUE;
	}

	/**
	*	{@return an {@code integer} hash code for this object}
	*/
	public default PsyInteger psyHashCode()
	{
		return PsyInteger.of(hashCode());
	}


	//static final PsyNamespace NAMESPACE=PsyNamespace.namespace(PsyObject.class);
	/*
	public default PsyObject psyConvert(PsyString oTypeName)
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
}
