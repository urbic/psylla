package coneforest.psi;

/**
 *	A representation of Ψ <code class="type">boolean</code> object.
 */
public class PsiBoolean
	extends PsiAbstractObject
	implements
		PsiAtomic,
		PsiLogical<PsiBoolean>
{
	private PsiBoolean(final boolean value)
	{
		this.value=value;
	}

	/**
	 *	Returns a string representation of a type name, the string
	 *	<code>"boolean"</code>.
	 *
	 *	@return a string representation of a type name.
	 */
	@Override
	public String getTypeName()
	{
		return "boolean";
	}

	/**
	 *	Returns a boolean value of this object.
	 *
	 *	@return a boolean value of this object.
	 */
	public boolean booleanValue()
	{
		return value;
	}

	/**
	 *	Returns a syntactic representation of this object boolean value, the
	 *	string <code>"true"</code> or <code>"false"</code>.
	 *
	 *	@return a string representing this object’s boolean value.
	 */
	@Override
	public String toString()
	{
		return ""+value;
	}

	/**
	 *	Returns a result of boolean negation of this object.
	 *
	 *	@return a result.
	 */
	@Override
	public PsiBoolean psiNot()
	{
		return PsiBoolean.valueOf(!value);
	}

	/**
	 *	Returns a result of boolean disjunction of this object and given
	 *	object.
	 *
	 *	@param bool given object.
	 *	@return a result.
	 */
	@Override
	public PsiBoolean psiOr(final PsiBoolean bool)
	{
		return PsiBoolean.valueOf(value || bool.value);
	}

	/**
	 *	Returns a result of boolean conjunction of this object and given
	 *	object.
	 *
	 *	@param bool given object.
	 *	@return a result.
	 */
	@Override
	public PsiBoolean psiAnd(final PsiBoolean bool)
	{
		return PsiBoolean.valueOf(value && bool.value);
	}

	/**
	 *	Returns a result of boolean exclusive disjunction of this object and
	 *	given object.
	 *
	 *	@param bool given object.
	 *	@return a result.
	 */
	@Override
	public PsiBoolean psiXor(final PsiBoolean bool)
	{
		return PsiBoolean.valueOf(value ^ bool.value);
	}

	/**
	 *	Returns a result of equality test of this object and given object. 
	 *
	 *	@return a result.
	 */
	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(equals(obj));
	}

	/**
	 *	Returns a hash code for this object (<code>1</code> or <code>0</code>).
	 *
	 *	@return a hash code.
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
				&& value==((PsiBoolean)object).value;
	}

	public static PsiBoolean valueOf(boolean b)
	{
		return b? TRUE: FALSE;
	}

	public static final PsiBoolean
		FALSE=new PsiBoolean(false),
		TRUE=new PsiBoolean(true);

	private final boolean value;

	static
	{
		TypeRegistry.put("boolean", PsiBoolean.class);
	}
}
