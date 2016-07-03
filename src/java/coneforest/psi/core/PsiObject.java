package coneforest.psi.core;
import coneforest.psi.*;

/**
*	A representation of Ψ-{@code object}, a basic type of Ψ objects.
*/
public interface PsiObject
	extends
		PsiConvertableToName,
		PsiConvertableToString
{
	/**
	*	Returns a string representation of a type name.
	*
	*	@return a string {@code "object"}.
	*/
	default public String typeName()
	{
		return "object";
	}

	default public PsiName psiType()
	{
		return new PsiName(typeName());
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

	default public PsiBoolean psiEq(final PsiObject o)
	{
		return PsiBoolean.valueOf(this==o);
	}

	default public PsiBoolean psiNe(final PsiObject o)
	{
		return psiEq(o).psiNot();
	}

	/**
	*	Returns a clone of this object.
	*
	*	@return a clone of this object.
	*/
	default public PsiObject psiClone()
	{
		return this;
	}

	/**
	*	Returns a Ψ-{@code string} representing this object.
	*
	*	@return a Ψ-{@code string} representing this object.
	*/
	@Override
	default public PsiString psiToString()
	{
		return new PsiString(toSyntaxString());
	}

	default public PsiString convert(Class<PsiString> clazz)
	{
		return new PsiString(toSyntaxString());
	}

	/**
	*	Returns a Ψ-{@code name} representing this object.
	*
	*	@return a Ψ-{@code name} representing this object.
	*/
	@Override
	default public PsiName psiToName()
	{
		return new PsiName(toSyntaxString());
	}

	default public String toSyntaxString()
	{
		return "-"+typeName()+"-";
	}

	// TODO
	default public PsiBoolean psiInstanceOf(PsiStringy stringy)
	{
		//Class<? extends PsiObject> clazz=TypeRegistry.get(stringy.getString());
		//return PsiBoolean.valueOf(clazz!=null && clazz.isInstance(this));
		return PsiBoolean.TRUE;
	}

	default public PsiInteger psiHashCode()
	{
		return PsiInteger.valueOf(hashCode());
	}

	/*
	default public PsiObject psiConvert(PsiName oTypeName)
		throws PsiException
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
			return (PsiObject)handle.invoke(this, type.getTypeClass());
		}
		catch(PsiException e)
		{
			throw e;
		}
		catch(Throwable e)
		{
			System.out.println(e);
			throw new PsiUnregisteredException();
		}
	}
	*/
}
