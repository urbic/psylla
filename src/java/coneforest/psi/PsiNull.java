package coneforest.psi;

/**
 *	A representation of Ψ <code class="type">null</code> object.
 */
public final class PsiNull
	extends PsiAbstractObject
	implements PsiAtomic
{
	/**
	 *	Returns a string representation of a type name.
	 *
	 *	@return <code class="constant">"null"</code> string.
	 */
	@Override
	public String getTypeName() { return "null"; }

	/**
	 *	Invokes this object in the context of the interpreter.
	 *
	 *	@param interpreter an interpreter
	 */
	@Override
	public void invoke(Interpreter interpreter)
	{
		if(isLiteral())
			interpreter.getOperandStack().push(this);
	}

	/**
	 *	Returns a syntactic representation of this object, the string <code
	 *	class="constant">"null"</code>.
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
	 *	“equal to” this one. Return value is <code class="constant">true</code>
	 *	if and only if other object has <code class="type">name</code> type.
	 *
	 *	@return a result.
	 */
	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiNull);
	}
}
