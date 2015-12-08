package coneforest.psi;

/**
 *	A type of basic Ψ object.
 */
public interface PsiObject
	extends
		PsiConvertableToName,
		PsiConvertableToString
{
	/**
	 *	Returns a string representation of a type name.
	 *
	 *	@return a type name.
	 */
	default public String getTypeName()
	{
		return "object";
	}

	default public PsiType<PsiObject> psiType()
	{
		return new PsiType<PsiObject>((Class<PsiObject>)getClass());
	}

	/*
	default public PsiCommand psiType()
	{
		return new PsiCommand(getTypeName()+"type");
	}
	*/

	/**
	 *	Execute this object in the context of an interpreter. Pushes this
	 *	object into interpreter’s operand stack.
	 *
	 *	@param interpreter an interpreter.
	 */
	default public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	/**
	 *	Invoke this object in the context of an interpreter. Pushes this
	 *	object into interpreter’s operand stack.
	 *
	 *	@param interpreter an interpreter.
	 */
	default public void invoke(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	default public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(this==obj);
	}

	default public PsiBoolean psiNe(final PsiObject obj)
	{
		return psiEq(obj).psiNot();
	}

	/**
	 *	Returns a clone of this object.
	 *
	 *	@return a clone.
	 */
	default public PsiObject psiClone()
	{
		return this;
	}

	/**
	 *	Returns a Ψ string representing this object.
	 *
	 *	@return a Ψ string representing this object.
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
	 *	Returns a Ψ name representing this object.
	 *
	 *	@return a Ψ string representing this object.
	 */
	@Override
	default public PsiName psiToName()
	{
		return new PsiName(toSyntaxString());
	}

	default public String toSyntaxString()
	{
		return "-"+getTypeName()+"-";
	}

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

	default public PsiObject psiConvert(PsiType type)
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
}
