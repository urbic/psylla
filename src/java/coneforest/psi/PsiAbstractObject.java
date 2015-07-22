package coneforest.psi;

/**
 *	A representation of basic Ψ-<code class="type">object</code>.
 */
abstract public class PsiAbstractObject
	implements PsiObject
{
	/**
	 *	Returns a string representation of a type name.
	 *
	 *	@return a type name.
	 */
	abstract public String getTypeName();

	public PsiCommand psiType()
	{
		return new PsiCommand(getTypeName()+"type");
	}

	/**
	 *	Execute this object in the context of an interpreter. Pushes this
	 *	object into interpreter’s operand stack.
	 */
	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	/**
	 *	Invoke this object in the context of an interpreter. Pushes this
	 *	object into interpreter’s operand stack.
	 */
	public void invoke(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(this==obj);
	}

	public PsiBoolean psiNe(final PsiObject obj)
	{
		return psiEq(obj).psiNot();
	}

	/**
	 *	Returns this object.
	 *
	 *	@return this object.
	 */
	public PsiObject psiClone()
	{
		return this;
	}

	/**
	 *	Returns a Ψ-<code class="type">string</code> representing this object.
	 *
	 *	@return a Ψ-<code class="type">string</code> representation of this object.
	 */
	public PsiString psiToString()
	{
		return new PsiString(toString());
	}

	public PsiName psiToName()
	{
		return new PsiName(toString());
	}

	/**
	 *	Returns a string representing this object.
	 *
	 *	@return a string <code class="constant">"-<em
	 *	class="replaceable">type</em>-"</code>, where <em
	 *	class="replaceable">type</em> is the type name of this object.
	 */
	@Override
	public String toString()
	{
		return "-"+getTypeName()+"-";
	}

	@Override
	public PsiBoolean psiIsA(PsiStringlike stringlike)
	{
		//Class<? extends PsiObject> clazz=TypeRegistry.get(stringlike.getString());
		//return PsiBoolean.valueOf(clazz!=null && clazz.isInstance(this));
		return PsiBoolean.TRUE;
	}
}
