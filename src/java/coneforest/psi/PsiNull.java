package coneforest.psi;

/**
 *	A representation of Ψ null object.
 */
public final class PsiNull
	extends PsiObject
	implements PsiAtomic
{
	/**
	 *	Returns a string representation of a type name.
	 *
	 *	@return <code>"null"</code> string.
	 */
	@Override
	public String getTypeName() { return "null"; }

	/**
	 *	Invoke this object in the context of the interpreter.
	 *
	 *	@param interpreter interpreter
	 */
	@Override
	public void invoke(Interpreter interpreter)
	{
		if(isLiteral())
			interpreter.getOperandStack().push(this);
	}

	/**
	 *	Returns a syntactic representation of an object, <code>"null"</code>.
	 *
	 *	@return a syntactic representation of an object.
	 */
	@Override
	public String toString()
	{
		return "null";
	}

	/**
	 *	Returns a boolean Ψ-object indicating whether some other Ψ-object is
	 *	“equal to” this one. Return value is <i>true</i> if and only if other
	 *	object has <code>name</code> type.
	 *
	 *	@return a result.
	 */
	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiNull);
	}
}
