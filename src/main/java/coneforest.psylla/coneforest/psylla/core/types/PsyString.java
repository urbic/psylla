package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyLimitCheck;
import coneforest.psylla.core.errors.PsyRangeCheck;
import coneforest.psylla.core.errors.PsySyntaxError;

/**
*	An implementation of {@code string}.
*/
@Type("string")
public class PsyString
	implements
		PsyTextual,
		PsyFormalArray<PsyInteger>
{
	/**
	*	Creates a new empty {@code string} object.
	*/
	public PsyString()
	{
		this("");
	}

	/**
	*	Creates a new {@code string} object whose buffer is initialized from
	*	string.
	*
	*	@param string a string.
	*/
	public PsyString(final String string)
	{
		this(new StringBuilder(string));
	}

	/**
	*	Creates a new {@code string} object with the supplied buffer.
	*
	*	@param buffer a buffer.
	*/
	public PsyString(final StringBuilder buffer)
	{
		this.buffer=buffer;
	}

	/**
	*	Returns the buffer.
	*
	*	@return a buffer.
	*/
	public StringBuilder getBuffer()
	{
		return buffer;
	}

	@Override
	public PsyString psyToString()
	{
		return this;
	}

	@Override
	public String stringValue()
	{
		return buffer.toString();
	}

	@Override
	public PsyString psyClone()
	{
		return new PsyString(stringValue());
	}

	@Override
	public PsyInteger get(final int index)
		throws PsyRangeCheck
	{
		try
		{
			return PsyInteger.of(buffer.charAt(index));
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public PsyString psyGetInterval(final PsyInteger oIndex, final PsyInteger oCount)
		throws PsyRangeCheck
	{
		int index=oIndex.intValue();
		int count=oCount.intValue();
		try
		{
			return new PsyString(buffer.substring(index, index+count));
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public void put(final int index, final PsyInteger oCharacter)
		throws PsyRangeCheck
	{
		try
		{
			buffer.setCharAt(index, (char)oCharacter.intValue());
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public void psyPutInterval(final PsyInteger oIndex, final PsyIterable<? extends PsyInteger> oIterable)
		throws PsyRangeCheck
	{
		int index=oIndex.intValue();
		if(index<0
				||
				oIterable instanceof PsyLengthy oLengthy
				&& index+oLengthy.length()>=length())
			throw new PsyRangeCheck();
		for(final var oCharacter: oIterable)
		{
			buffer.setCharAt(index++, (char)oCharacter.intValue());
			if(index==length())
				break;
		}
	}

	@Override
	public void psyAppend(final PsyInteger oCharacter)
		throws PsyLimitCheck
	{
		if(length()==Integer.MAX_VALUE)
			throw new PsyLimitCheck();
		buffer.append((char)oCharacter.intValue());
	}

	@Override
	public void insert(final int index, final PsyInteger oCharacter)
		throws PsyRangeCheck
	{
		try
		{
			buffer.insert(index, (char)oCharacter.intValue());
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public void psyInsertAll(final PsyInteger oIndex, PsyIterable<? extends PsyInteger> oIterable)
		throws PsyRangeCheck
	{
		int index=oIndex.intValue();
		try
		{
			if(oIterable instanceof PsyString oString)
			{
				// Take care when attempting to insert this object into itself
				buffer.insert(index, this==oIterable? buffer.toString(): oString.buffer);
				return;
			}

			for(final var oCharacter: oIterable)
				// TODO
				buffer.insert(index++, (char)oCharacter.intValue());
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public void delete(final int index)
		throws PsyRangeCheck
	{
		try
		{
			buffer.deleteCharAt(index);
		}
		catch(final StringIndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public PsyInteger extract(final int index)
		throws PsyRangeCheck
	{
		try
		{
			final PsyInteger oResult=get(index);
			buffer.deleteCharAt(index);
			return oResult;
		}
		catch(final StringIndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public PsyString psyExtractInterval(final PsyInteger oStart, final PsyInteger oLength)
		throws PsyError
	{
		final PsyString oResult=psyGetInterval(oStart, oLength);
		buffer.replace(oStart.intValue(), oStart.intValue()+oLength.intValue(), "");
		return oResult;
	}

	@Override
	public PsyString psySlice(final PsyIterable<PsyInteger> oIndices)
		throws PsyError
	{
		final PsyString oValues=new PsyString();
		for(final var oIndex: oIndices)
			oValues.psyAppend(psyGet(oIndex));
		return oValues;
	}

	/*
	public PsyString psyJoin(final PsyFormalArray<? extends PsyString> oArray)
		throws PsyError
	{
		if(oArray.isEmpty())
			return new PsyString();
		PsyString result=((PsyString)oArray.get(0)).psyClone();
		for(int i=1; i<oArray.length(); i++)
		{
			result.psyAppendAll(this);
			result.psyAppendAll(oArray.get(i));
		}
		return result;
	}
	*/

	/*
	public PsyDict psySearch(PsyRegExp regexp)
	{
		PsyDict result=null;
		java.util.regex.Pattern pattern=regexp.getPattern();
		java.util.regex.Matcher matcher=pattern.matcher(buffer);
		matcher.find();
		return result;
	}
	*/

	/*
	public PsyMatcher PsyMatches(PsyRegExp regexp)
	{
	}
	*/

	@Override
	public void psySetLength(final PsyInteger oLength)
		throws PsyLimitCheck, PsyRangeCheck
	{
		final long length=oLength.longValue();
		if(length>Integer.MAX_VALUE)
			throw new PsyLimitCheck();
		try
		{
			buffer.setLength((int)length);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public void psyClear()
	{
		buffer.delete(0, buffer.length());
	}

	@Override
	public PsyString psyReverse()
	{
		return new PsyString((new StringBuilder(buffer)).reverse());
	}

	@Override
	public PsyString psyUpperCase()
	{
		return new PsyString(buffer.toString().toUpperCase());
	}

	@Override
	public PsyString psyLowerCase()
	{
		return new PsyString(buffer.toString().toLowerCase());
	}

	@Override
	public int length()
	{
		return buffer.length();
	}

	@Override
	public boolean equals(final Object object)
	{
		return getClass().isInstance(object)
				&& psyEq((PsyString)object).booleanValue();
	}

	@Override
	public int hashCode()
	{
		return buffer.hashCode();
	}

	@Override
	public String toSyntaxString()
	{
		final var sb=new StringBuilder();
		for(int i=0; i<buffer.length(); i++)
		{
			final char c=buffer.charAt(i);
			switch(c)
			{
				case '\u0000':
					sb.append("\\0");
					break;
				case '\u0007':
					sb.append("\\a");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '\u000B':
					sb.append("\\v");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\u001B':
					sb.append("\\e");
					break;
				case '\"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				default:
					sb.append(c);
			}
		}
		return "\""+sb.toString()+"\"";
	}

	public static PsyString parseLiteral(final String image)
		throws PsySyntaxError
	{
		final var sb=new StringBuilder();
		for(int i=1; i<image.length()-1; i++)
		{
			final var c=image.charAt(i);
			switch(c)
			{
				case '\\':
					i++;
					switch(image.charAt(i))
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
						case '"':
							sb.append('"');
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
		return new PsyString(sb);
	}

	private final StringBuilder buffer;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity01
				("string", PsyString::new),
		};

}
