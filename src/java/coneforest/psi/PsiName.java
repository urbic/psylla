package coneforest.psi;

/**
 *	A representation of Ψ-{@code name} object.
 */
public class PsiName
	implements PsiStringy
{
	/**
	 *	Instantiate a new Ψ-{@code name} object with given name.
	 *
	 *	@param cs a name.
	 */
	public PsiName(CharSequence cs)
	{
		name=cs.toString();
		//name=cs.toString();
	}

	/**
	 *	Instantiate a new Ψ-{@code name} object with name given as Ψ-{@code
	 *	stringy} object.
	 *
	 *	@param stringy a stringy.
	 */
	public PsiName(PsiStringy stringy)
	{
		this(stringy.getString());
	}

	/**
	 *	@return a {@code "name"} string.
	 */
	@Override
	public String getTypeName()
	{
		return "name";
	}
	/**
	 *	Returns a string value of this object’s value.
	 *
	 *	@return a string value of this object.
	 */

	@Override
	public String getString()
	{
		return name;
	}

	@Override
	public PsiString psiToString()
	{
		return new PsiString(getString());
	}

	/**
	 *	@return a Ψ-{@code integer} length (in characters) of this name.
	 */
	@Override
	public PsiInteger psiLength()
	{
		return PsiInteger.valueOf(name.length());
	}

	/**
	 *	Converts all of the characters in this object to upper case according
	 *	to default locale and returns a new Ψ-{@code name} object representing
	 *	the result of the conversion.
	 *
	 *	@return a Ψ-{@code name} result of upper-casing.
	 */
	@Override
	public PsiName psiUpperCase()
	{
		return new PsiName(name.toUpperCase());
	}

	/**
	 *	Converts all of the characters in this object to lower case according
	 *	to default locale and returns a new Ψ-{@code name} object representing
	 *	the result of the conversion.
	 *
	 *	@return a Ψ-{@code name} result of lower-casing.
	 */
	@Override
	public PsiName psiLowerCase()
	{
		return new PsiName(name.toLowerCase());
	}

	/**
	 *	Returns a syntactic representation of this object’s value. Returns a
	 *	value string prepended with {@code /}.
	 *
	 *	@return a syntactic representation of this object’s value.
	 */
	@Override
	public String toSyntaxString()
	{
		return "/"+name;
	}

	/**
	 *	Returns a Ψ-{@code boolean} indicating whether some other Ψ-object is
	 *	“equal to” this one. Return value is <i>true</i> if and only if other
	 *	object has {@code name} type and its value is equal to this one’s.
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
