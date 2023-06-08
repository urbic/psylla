package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsySyntaxError;

/**
*	A representation of {@code name}, an immutable string.
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

	public static PsyName parseLiteral(final String image)
		throws PsySyntaxError
	{
		if(image.charAt(0)=='/')
			return new PsyName(image.substring(1).intern());
		final var sb=new StringBuilder();
		for(int i=1; i<image.length()-1; i++)
		{
			final var c=image.charAt(i);
			switch(c)
			{
				case '\\':
					switch(image.charAt(++i))
					{
						case '0':
							sb.append('\u0000');
							break;
						case 'a':
							sb.append('\u0007');
							break;
						case 'n':
							sb.append('\n');
							break;
						case 'r':
							sb.append('\r');
							break;
						case 't':
							sb.append('\t');
							break;
						case 'v':
							sb.append('\u000B');
							break;
						case 'f':
							sb.append('\f');
							break;
						case 'e':
							sb.append('\u001B');
							break;
						case '\'':
							sb.append('\'');
							break;
						case '\\':
							sb.append('\\');
							break;
						case '\n':
							break;
						case 'u':
							sb.append(Character.toChars(Integer.valueOf(image.substring(i+1, i+5), 16)));
							i+=4;
							break;
						case 'c':
							{
								final var ch=image.charAt(++i);
								sb.append(Character.toChars(ch+(ch<64? 64: -64)));
							}
							break;
						case 'x':
							try
							{
								final var j=image.indexOf('}', i+2);
								sb.append(Character.toChars(Integer.valueOf(image.substring(i+2, j), 16)));
								i=j;
							}
							catch(final IllegalArgumentException ex)
							{
								throw new PsySyntaxError();
							}
							break;
						case 'N':
							try
							{
								final var j=image.indexOf('}', i+2);
								final var cp=Character.codePointOf(image.substring(i+2, j));
								// TODO
								sb.append((char)cp);
								i=j;
							}
							catch(final IllegalArgumentException ex)
							{
								throw new PsySyntaxError();
							}
							break;
					}
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return new PsyName(sb.toString().intern());
	}

	protected final String name;
}
