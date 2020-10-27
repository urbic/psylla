package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code object}, a basic type of Ψ objects.
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
		//return "object";
		return getClass().getAnnotation(Type.class).value();
		//return java.lang.invoke.MethodHandles.lookup().lookupClass()
		//	.getAnnotation(Type.class).value();
	}

	default public PsyName psyType()
	{
		return new PsyName(typeName());
	}

	/**
	*	Execute this object in the context of an interpreter. Pushes this
	*	object into interpreter’s operand stack.
	*
	*	@param interpreter an interpreter.
	*/
	default public void execute(final Interpreter interpreter)
	{
		interpreter.operandStack().push(this);
	}

	/**
	*	Invoke this object in the context of an interpreter. Pushes this
	*	object into interpreter’s operand stack.
	*
	*	@param interpreter an interpreter.
	*/
	default public void invoke(final Interpreter interpreter)
	{
		interpreter.operandStack().push(this);
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
	*	Returns a Ψ-{@code string} representing this object.
	*
	*	@return a Ψ-{@code string} representing this object.
	*/
	@Override
	default public PsyString psyToString()
	{
		return new PsyString(toSyntaxString());
	}

	/**
	*	Returns a Ψ-{@code name} representing this object.
	*
	*	@return a Ψ-{@code name} representing this object.
	*/
	@Override
	default public PsyName psyToName()
	{
		return new PsyName(toSyntaxString());
	}

	default public String toSyntaxString()
	{
		return '|'+typeName()+'|';
	}

	// TODO
	default public PsyString convert(final Class<PsyString> clazz)
	{
		return new PsyString(toSyntaxString());
	}

	// TODO
	default public PsyBoolean psyInstanceOf(final PsyStringy oTypeName)
	{
		//Class<? extends PsyObject> clazz=TypeRegistry.get(stringy.getString());
		//return PsyBoolean.valueOf(clazz!=null && clazz.isInstance(this));

		final var interpreter=Interpreter.currentInterpreter();
		return PsyBoolean.TRUE;
	}

	/**
	*	Returns a Ψ-{@code integer} hash code for this object.
	*
	*	@return a Ψ-{@code integer} hash code for this object.
	*/
	default public PsyInteger psyHashCode()
	{
		return PsyInteger.valueOf(hashCode());
	}

	public static void register(final Interpreter interpreter)
	{
		//final var namespace=PsyNamespace.forName("object");
		final var oNamespace=interpreter.namespacePool().namespace("object");
		/*oNamespace.registerOperators
			(
				new PsyOperator.Arity11<PsyObject>
					("clone", PsyObject::psyClone),
				new PsyOperator.Arity21<PsyObject, PsyObject>
					("eq", PsyObject::psyEq),
				new PsyOperator.Arity11<PsyObject>
					("hashcode", PsyObject::psyHashCode),
				new PsyOperator.Arity21<PsyObject, PsyObject>
					("ne", PsyObject::psyNe),
				new PsyOperator.Arity11<PsyObject>
					("syntax", PsyObject::psySyntax),
				new PsyOperator.Arity11<PsyObject>
					("type", PsyObject::psyType)
			);*/
		System.out.println(PsyObject.class.getAnnotation(Type.class).value()+" REGISTERED");
	}

	public static String classTypeName()
	{
		return java.lang.invoke.MethodHandles.lookup().lookupClass()
			.getAnnotation(Type.class).value();

		//final var prefix=PsyInteger.class.getAnnotation(Type.class).value();
		//interpreter.namespacePool().obtain(prefix);
		//System.out.println("Registered: "+prefix);

		//System.out.println(getClass().getAnnotation(Type.class).value());
	}

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
}
