package coneforest.psi.core;

/**
*	A representation of Ψ-{@code name}, a type of immutable string.
*/
public class PsiName
	implements
		PsiAtomic,
		PsiStringy
{
	/**
	*	Instantiate a new Ψ-{@code name} object with given name.
	*
	*	@param cs a name.
	*/
	public PsiName(final CharSequence cs)
	{
		name=cs.toString();
	}

	/**
	*	Instantiate a new Ψ-{@code name} object with name given as Ψ-{@code
	*	stringy} object.
	*
	*	@param oStringy a stringy.
	*/
	public PsiName(final PsiStringy oStringy)
	{
		this(oStringy.stringValue());
	}

	/**
	*	@return a {@code "name"} string.
	*/
	@Override
	public String typeName()
	{
		return "name";
	}
	/**
	*	Returns a string value of this object’s value.
	*
	*	@return a string value of this object.
	*/

	@Override
	public String stringValue()
	{
		return name;
	}

	@Override
	public PsiString psiToString()
	{
		return new PsiString(name);
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
		if(name.length()==0)
			return "''";
		if(name.length()==1)
		{
			final char c=name.charAt(0);
			return (c>='A' && c<='Z'
					|| c>='a' && c<='z'
					|| c=='['
					|| c==']'
					|| c=='<'
					|| c=='>'
					|| c=='('
					|| c==')'
					|| c=='?'
					|| c=='=')? "/"+name: "'"+name+"'";
		}
		boolean slashed=true;
		if(name.charAt(0)>='0' && name.charAt(0)<='9')
			slashed=false;
		else
		{
			for(int i=0; i<name.length(); i++)
			{
				char c=name.charAt(i);
				slashed
					=(c>='0' && c<='9'
						|| c>='A' && c<='Z'
						|| c>='a' && c<='z'
						|| c=='_'
						|| c=='.'
						|| c=='-'
						|| c=='+'
						|| c=='='
						|| c=='$');
				if(!slashed)
					break;
			}
		}
		return slashed? "/"+name: "'"+name+"'";
	}

	/**
	*	Returns a Ψ-{@code boolean} indicating whether some other Ψ-object is
	*	“equal to” this one. Return value is {@code true} if and only if other
	*	object has Ψ-{@code name} type and its value is equal to this one’s.
	*
	*	@return a result.
	*/
	@Override
	public boolean equals(final Object object)
	{
		return object instanceof PsiName
				&& psiEq((PsiName)object).booleanValue();
	}

	@Override
	public int hashCode()
	{
		return stringValue().hashCode();
	}

	protected final String name;
}
