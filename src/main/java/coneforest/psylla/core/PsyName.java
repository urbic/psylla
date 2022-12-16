package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code name} object, an immutable string.
*/
@Type("name")
public class PsyName
	implements
		PsyAtomic,
		PsyTextual
{
	/**
	*	Instantiate a new {@code name} object from the given value.
	*
	*	@param cs a given value.
	*/
	public PsyName(final CharSequence cs)
	{
		name=cs.toString();
	}

	/**
	*	Instantiate a new {@code name} object from the value given as {@code
	*	textual} object.
	*
	*	@param oTextual a {@code textual} object.
	*/
	public PsyName(final PsyTextual oTextual)
	{
		this(oTextual.stringValue());
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
	public PsyString psyToString()
	{
		return new PsyString(name);
	}

	/**
	*	@return an {@code integer} length (in characters) of this name.
	*/
	@Override
	public PsyInteger psyLength()
	{
		return PsyInteger.of(name.length());
	}

	/**
	*	Converts all of the characters in this object to upper case according
	*	to default locale and returns a new {@code name} object representing
	*	the result of the conversion.
	*
	*	@return a {@code name} result of upper-casing.
	*/
	@Override
	public PsyName psyUpperCase()
	{
		return new PsyName(name.toUpperCase());
	}

	/**
	*	Converts all of the characters in this object to lower case according
	*	to default locale and returns a new {@code name} object representing
	*	the result of the conversion.
	*
	*	@return a {@code name} result of lower-casing.
	*/
	@Override
	public PsyName psyLowerCase()
	{
		return new PsyName(name.toLowerCase());
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
	*	Returns a {@code boolean} object indicating whether some other object
	*	is “equal to” this one. Return value is {@code true} if and only if
	*	other object has {@code name} type and its value is equal to this
	*	one’s.
	*
	*	@return a {@code boolean} result.
	*/
	@Override
	public boolean equals(final Object object)
	{
		return object instanceof PsyName
				&& psyEq((PsyName)object).booleanValue();
	}

	@Override
	public int hashCode()
	{
		return stringValue().hashCode();
	}

	protected final String name;
}
