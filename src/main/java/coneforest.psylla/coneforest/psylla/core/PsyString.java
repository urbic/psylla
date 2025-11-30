package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code string}, an immutable string.
*/
@Type("string")
public class PsyString
	implements PsyTextual, PsyValue, PsyConcatenable<PsyString>
{
	protected final String string;

	/**
	*	Instantiate a new {@code string} object from the given value.
	*
	*	@param cs a given value.
	*/
	public PsyString(final CharSequence cs)
	{
		string=cs.toString();
	}

	/**
	*	Instantiate a new {@code string} object from the value given as {@code textual} object.
	*
	*	@param oTextual a {@code textual} object.
	*/
	public PsyString(final PsyTextual oTextual)
	{
		this(oTextual.stringValue());
	}

	/**
	*	{@return a string value of this object}
	*/
	@Override
	public String stringValue()
	{
		return string;
	}

	@Override
	public PsyStringBuffer psyToStringBuffer()
	{
		return new PsyStringBuffer(string);
	}

	/**
	*	@return an {@code integer} length (in characters) of this {@code string}.
	*/
	@Override
	public PsyInteger psyLength()
	{
		return PsyInteger.of(string.length());
	}

	@Override
	public PsyString psyConcat(final PsyString oString)
	{
		return new PsyString(string+oString.string);
	}

	/**
	*	Converts all of the characters in this object to upper case according to default locale and
	*	returns a new {@code string} object representing the result of the conversion.
	*
	*	@return a {@code string} result of upper-casing.
	*/
	@Override
	public PsyString psyUpperCase()
	{
		return new PsyString(string.toUpperCase());
	}

	/**
	*	Converts all of the characters in this object to lower case according to default locale and
	*	returns a new {@code string} object representing the result of the conversion.
	*
	*	@return a {@code string} result of lower-casing.
	*/
	@Override
	public PsyString psyLowerCase()
	{
		return new PsyString(string.toLowerCase());
	}

	/**
	*	{@return a syntactic representation of this object’s value} Returns a value string prepended
	*	with {@code /}.
	*/
	@Override
	public String toSyntaxString()
	{
		if(string.length()==0)
			return "''";
		if(string.length()==1)
		{
			final char c=string.charAt(0);
			return (c>='A' && c<='Z'
					|| c>='a' && c<='z'
					|| c=='['
					|| c==']'
					|| c=='<'
					|| c=='>'
					|| c=='('
					|| c==')'
					|| c=='?'
					|| c=='=')? "/"+string: "'"+string+"'";
		}
		boolean slashed=true;
		if(string.charAt(0)>='0' && string.charAt(0)<='9')
			slashed=false;
		else
		{
			for(int i=0; i<string.length(); i++)
			{
				final char c=string.charAt(i);
				slashed=c>='0' && c<='9'
						|| c>='A' && c<='Z'
						|| c>='a' && c<='z'
						|| c=='_'
						|| c=='.'
						|| c=='-'
						|| c=='+'
						|| c=='='
						|| c=='$';
				if(!slashed)
					break;
			}
		}
		return slashed? "/"+string: "'"+string+"'";
	}

	/**
	*	{@return a {@code boolean} object indicating whether some other object is “equal to” this
	*	one} Return value is {@code true} if and only if other object has {@code string} type and
	*	its value is equal to this one’s.
	*/
	@Override
	public boolean equals(final Object object)
	{
		return object instanceof PsyString oString
				&& string.equals(oString.string);
	}

	@Override
	public int hashCode()
	{
		return string.hashCode();
	}

	public static PsyString parseLiteral(final String image)
		throws PsySyntaxErrorException
	{
		if(image.charAt(0)=='/')
			return new PsyString(image.substring(1).intern());
		final var sb=new StringBuilder();
		for(int i=1; i<image.length()-1; i++)
		{
			final var c=image.charAt(i);
			switch(c)
			{
				case '\\':
					switch(image.charAt(++i))
					{
						case '0'->sb.append('\u0000');
						case 'a'->sb.append('\u0007');
						case 'n'->sb.append('\n');
						case 'r'->sb.append('\r');
						case 't'->sb.append('\t');
						case 'v'->sb.append('\u000B');
						case 'f'->sb.append('\f');
						case 'e'->sb.append('\u001B');
						case '\''->sb.append('\'');
						case '\\'->sb.append('\\');
						case '\n'->{}
						case 'u'->
							{
								sb.append(Character.toChars(Integer.valueOf(image.substring(i+1, i+5), 16)));
								i+=4;
							}
						case 'c'->
							{
								final var ch=image.charAt(++i);
								sb.append(Character.toChars(ch+(ch<64? 64: -64)));
							}
						case 'x'->
							{
								try
								{
									final var j=image.indexOf('}', i+2);
									sb.append(Character.toChars(Integer.valueOf(image.substring(i+2, j), 16)));
									i=j;
								}
								catch(final IllegalArgumentException ex)
								{
									throw new PsySyntaxErrorException();
								}
							}
						case 'o'->
							{
								try
								{
									final var j=image.indexOf('}', i+2);
									sb.append(Character.toChars(Integer.valueOf(image.substring(i+2, j), 8)));
									i=j;
								}
								catch(final IllegalArgumentException ex)
								{
									throw new PsySyntaxErrorException();
								}
							}
						case 'N'->
							{
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
									throw new PsySyntaxErrorException();
								}
							}
					}
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return new PsyString(sb.toString().intern());
	}
}
