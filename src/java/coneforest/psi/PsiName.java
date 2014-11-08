package coneforest.psi;

/**
 *	Representation of Ψ name object.
 */
public class PsiName
	extends PsiAbstractStringlike
{
	/**
	 *	Instantiate a new Ψ name object with given name.
	 *
	 *	@param name a name.
	 */
	public PsiName(String name)
	{
		this.name=name;
	}

	/**
	 *	Instantiate a new Ψ name object with name given as Ψ stringlike.
	 *
	 *	@param stringlike a stringlike.
	 */
	public PsiName(PsiStringlike stringlike)
	{
		this(stringlike.getString());
	}

	/**
	 *	Returns a string representation of this object’s value.
	 *
	 *	@return an object value.
	 */
	@Override
	public String getString()
	{
		return name;
	}

	/**
	 *	Returns a string representation of this object’s type name.
	 *
	 *	@return <code>"name"</code> string.
	 */
	@Override
	public String getTypeName()
	{
		return "name";
	}

	/**
	 *	Execute this object in the context of the interpreter.
	 *
	 *	@param interpreter interpreter
	 */
	public void execute(Interpreter interpreter)
	{
		if(isExecutable())
		{
			/*
			PsiObject obj=interpreter.getDictionaryStack().load(this);
			if(obj==null)
				interpreter.error("undefined");
			else if(obj instanceof PsiArray && ((PsiArray)obj).isExecutable())
			{
				//((PsiArray)obj).invoke(interpreter);
				obj.invoke(interpreter);
			}
			else
				obj.execute(interpreter);
			*/
			try
			{
				interpreter.getDictionaryStack().load(this).invoke(interpreter);
			}
			catch(PsiException e)
			{
				interpreter.error(e, this);
			}
		}
		else
			interpreter.getOperandStack().push(this);
	}

	/**
	 *	Returns a length of this object’s value as a Ψ integer.
	 *
	 *	@return a length.
	 */
	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(name.length());
	}

	/**
	 *	Returns a Ψ boolean indicating whether or not this object’s value is
	 *	empty.
	 *
	 *	@return a result.
	 */
	@Override
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(name.length()==0);
	}

	/**
	 *	Returns a syntactic representation of this object’s value. For
	 *	executable names, returns a value string. For literal names, returns a
	 *	value string prepended with <code>/</code>.
	 *
	 *	@return a syntactic representation of this object’s value.
	 */
	@Override
	public String toString()
	{
		return (isExecutable()? "": "/")+getString();
	}

	/**
	 *	Returns a Ψ boolean indicating whether some other Ψ-object is “equal
	 *	to” this one. Return value is <i>true</i> if and only if other object
	 *	has <code>name</code> type and its value is equal to this one’s.
	 *
	 *	@return a result.
	 */
	@Override
	public boolean equals(Object object)
	{
		return object instanceof PsiName
				&& psiEq((PsiName)object).booleanValue();
	}

	private final String name;
}
