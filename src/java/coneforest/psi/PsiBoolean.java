package coneforest.psi;

/**
 *	A representation of Ψ boolean object.
 */
public class PsiBoolean
	extends PsiObject
	implements PsiAtomic, PsiLogical<PsiBoolean>
{
	public PsiBoolean(final boolean value)
	{
		this.value=value;
	}

	/**
	 *	Returns a string representation of a type name
	 *	(<code>"boolean"</code>).
	 *
	 *	@return a string representation of a type name.
	 */
	public String getTypeName()
	{
		return "boolean";
	}

	/**
	 *	Returns a boolean value of this object.
	 *
	 *	@return a boolean value of this object.
	 */
	public Boolean booleanValue()
	{
		return value;
	}

	/**
	 *	Returns a string representing this object’s boolean value
	 *	(<code>"true"</code> or <code>"false"</code>).
	 *
	 *	@return a string representing this object’s boolean value.
	 */
	public String toString()
	{
		return value? "true": "false";
	}

	public PsiBoolean psiNot()
	{
		return new PsiBoolean(!value);
	}

	public PsiBoolean psiOr(final PsiBoolean bool)
	{
		return new PsiBoolean(value || bool.value);
	}

	public PsiBoolean psiAnd(final PsiBoolean bool)
	{
		return new PsiBoolean(value && bool.value);
	}

	public PsiBoolean psiXor(final PsiBoolean bool)
	{
		return new PsiBoolean(value ^ bool.value);
	}

	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiBoolean
				&& value==((PsiBoolean)obj).value);
	}
	
	/**
	 *	Returns a hash code for this object (<code>1</code> or <code>0</code>).
	 *
	 *	@return a hash code for this object.
	 */
	@Override
	public int hashCode()
	{
		return value? 1: 0;
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof PsiBoolean
				&& psiEq((PsiBoolean)object).value;
	}

	private final boolean value;
}
