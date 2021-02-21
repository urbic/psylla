package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	An implementation of Ψ-{@code string} object.
*/
@Type("string")
public class PsyString
	implements
		PsyStringy,
		PsyArraylike<PsyInteger>
{
	/**
	*	Creates a new empty Ψ-{@code string}.
	*/
	public PsyString()
	{
		this("");
	}

	/**
	*	Creates a new Ψ-{@code string} whose buffer is initialized from string.
	*
	*	@param string a string.
	*/
	public PsyString(final String string)
	{
		this(new StringBuilder(string));
	}

	/**
	*	Creates a new Ψ-{@code string} with the supplied buffer.
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
		throws PsyException
	{
		try
		{
			return PsyInteger.valueOf(buffer.charAt(index));
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyString psyGetInterval(final PsyInteger oIndex, final PsyInteger oCount)
		throws PsyException
	{
		int index=oIndex.intValue();
		int count=oCount.intValue();
		try
		{
			return new PsyString(buffer.substring(index, index+count));
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void put(final int index, final PsyInteger oCharacter)
		throws PsyException
	{
		try
		{
			buffer.setCharAt(index, (char)oCharacter.intValue());
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void psyPutInterval(final PsyInteger oIndex, final PsyIterable<? extends PsyInteger> oIterable)
		throws PsyException
	{
		int index=oIndex.intValue();
		if(index<0
				||
				oIterable instanceof PsyLengthy
				&& index+((PsyLengthy)oIterable).length()>=length())
			throw new PsyRangeCheckException();
		for(final var oCharacter: oIterable)
		{
			buffer.setCharAt(index++, (char)oCharacter.intValue());
			if(index==length())
				break;
		}
	}

	@Override
	public void psyAppend(final PsyInteger oCharacter)
		throws PsyLimitCheckException
	{
		if(length()==Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		buffer.append((char)oCharacter.intValue());
	}

	@Override
	public void insert(final int index, final PsyInteger oCharacter)
		throws PsyException
	{
		try
		{
			buffer.insert(index, (char)oCharacter.intValue());
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void psyInsertAll(final PsyInteger oIndex, PsyIterable<? extends PsyInteger> oIterable)
		throws PsyException
	{
		int index=oIndex.intValue();
		try
		{
			if(oIterable instanceof PsyString)
			{
				// Take care when attempting to insert this object into itself
				buffer.insert(index, this==oIterable? buffer.toString(): ((PsyString)oIterable).buffer);
				return;
			}

			for(final var oCharacter: oIterable)
				buffer.insert(index++, (char)oCharacter.intValue());
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void delete(final int index)
		throws PsyException
	{
		try
		{
			buffer.deleteCharAt(index);
		}
		catch(final StringIndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyInteger extract(final int index)
		throws PsyException
	{
		try
		{
			final PsyInteger oResult=get(index);
			buffer.deleteCharAt(index);
			return oResult;
		}
		catch(final StringIndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyString psyExtractInterval(final PsyInteger oStart, final PsyInteger oLength)
		throws PsyException
	{
		final PsyString oResult=psyGetInterval(oStart, oLength);
		buffer.replace(oStart.intValue(), oStart.intValue()+oLength.intValue(), "");
		return oResult;
	}

	@Override
	public PsyString psySlice(final PsyIterable<PsyInteger> oIndices)
		throws PsyException
	{
		final PsyString oValues=new PsyString();
		for(final var oIndex: oIndices)
			oValues.psyAppend(psyGet(oIndex));
		return oValues;
	}

	/*
	public PsyString psyJoin(final PsyArraylike<? extends PsyString> arraylike)
		throws PsyException
	{
		if(arraylike.isEmpty())
			return new PsyString();
		PsyString result=((PsyString)arraylike.get(0)).psyClone();
		for(int i=1; i<arraylike.length(); i++)
		{
			result.psyAppendAll(this);
			result.psyAppendAll(arraylike.get(i));
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
		throws PsyException
	{
		final long length=oLength.longValue();
		if(length>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		try
		{
			buffer.setLength((int)length);
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
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

	private final StringBuilder buffer;

}
