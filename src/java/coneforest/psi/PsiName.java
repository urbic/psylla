package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">name</code> object.
 */
public class PsiName
	implements PsiStringlike
{
	/**
	 *	Instantiate a new Ψ name object with given name.
	 *
	 *	@param cs a name.
	 */
	public PsiName(CharSequence cs)
	{
		name=cs.toString().intern();
		//name=cs.toString();
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

	@Override
	public PsiString psiToString()
	{
		return new PsiString(getString());
	}

	/**
	 *	Returns a length of this object’s value as a Ψ integer.
	 *
	 *	@return a length.
	 */
	@Override
	public PsiInteger psiLength()
	{
		return PsiInteger.valueOf(name.length());
	}

	@Override
	public PsiName psiUpperCase()
	{
		return new PsiName(name.toUpperCase());
	}

	@Override
	public PsiName psiLowerCase()
	{
		return new PsiName(name.toLowerCase());
	}

	/**
	 *	Returns a syntactic representation of this object’s value. Returns a
	 *	value string prepended with <code>/</code>.
	 *
	 *	@return a syntactic representation of this object’s value.
	 */
	@Override
	public String toSyntaxString()
	{
		return "/"+name;
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

	@Override
	public int hashCode()
	{
		return getString().hashCode();
	}

	private final String name;
}
