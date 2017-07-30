package coneforest.psi.core;

/**
*	An implementation of Ψ-{@code string} object.
*/
@coneforest.psi.Type("string")
public class PsiString
	implements
		PsiStringy,
		PsiArraylike<PsiInteger>
{
	/**
	*	Creates a new empty Ψ-{@code string}.
	*/
	public PsiString()
	{
		this("");
	}

	/**
	*	Creates a new Ψ-{@code string} whose buffer is initialized from string.
	*
	*	@param string a string.
	*/
	public PsiString(final String string)
	{
		this(new StringBuilder(string));
	}

	/**
	*	Creates a new Ψ-{@code string} with the supplied buffer.
	*
	*	@param buffer a buffer.
	*/
	public PsiString(final StringBuilder buffer)
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
	public PsiString psiToString()
	{
		return this;
	}

	@Override
	public String stringValue()
	{
		return buffer.toString();
	}

	@Override
	public PsiString psiClone()
	{
		return new PsiString(stringValue());
	}

	@Override
	public PsiInteger get(final int index)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(buffer.charAt(index));
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiString psiGetInterval(final PsiInteger oIndex, final PsiInteger oCount)
		throws PsiException
	{
		int index=oIndex.intValue();
		int count=oCount.intValue();
		try
		{
			return new PsiString(buffer.substring(index, index+count));
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void put(final int index, final PsiInteger oCharacter)
		throws PsiException
	{
		try
		{
			buffer.setCharAt(index, (char)oCharacter.intValue());
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void psiPutInterval(final PsiInteger oIndex, final PsiIterable<? extends PsiInteger> oIterable)
		throws PsiException
	{
		int index=oIndex.intValue();
		if(index<0
				||
				oIterable instanceof PsiLengthy
				&& index+((PsiLengthy)oIterable).length()>=length())
			throw new PsiRangeCheckException();
		for(PsiInteger oCharacter: oIterable)
		{
			buffer.setCharAt(index++, (char)oCharacter.intValue());
			if(index==length())
				break;
		}
	}

	@Override
	public void psiAppend(final PsiInteger oCharacter)
		throws PsiLimitCheckException
	{
		if(length()==Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		buffer.append((char)oCharacter.intValue());
	}

	@Override
	public void insert(final int index, final PsiInteger oCharacter)
		throws PsiException
	{
		try
		{
			buffer.insert(index, (char)oCharacter.intValue());
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void psiInsertAll(final PsiInteger oIndex, PsiIterable<? extends PsiInteger> oIterable)
		throws PsiException
	{
		int index=oIndex.intValue();
		try
		{
			if(oIterable instanceof PsiString)
			{
				// Take care when attempting to insert this object into itself
				buffer.insert(index, this==oIterable? buffer.toString(): ((PsiString)oIterable).buffer);
				return;
			}

			for(PsiInteger oCharacter: oIterable)
				buffer.insert(index++, (char)oCharacter.intValue());
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void delete(final int index)
		throws PsiException
	{
		try
		{
			buffer.deleteCharAt(index);
		}
		catch(final StringIndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiInteger extract(final int index)
		throws PsiException
	{
		try
		{
			final PsiInteger oResult=get(index);
			buffer.deleteCharAt(index);
			return oResult;
		}
		catch(final StringIndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiString psiExtractInterval(final PsiInteger oStart, final PsiInteger oLength)
		throws PsiException
	{
		final PsiString oResult=psiGetInterval(oStart, oLength);
		buffer.replace(oStart.intValue(), oStart.intValue()+oLength.intValue(), "");
		return oResult;
	}

	@Override
	public PsiString psiSlice(final PsiIterable<PsiInteger> oIndices)
		throws PsiException
	{
		final PsiString oValues=new PsiString();
		for(PsiInteger oIndex: oIndices)
			oValues.psiAppend(psiGet(oIndex));
		return oValues;
	}

	/*
	public PsiString psiJoin(final PsiArraylike<? extends PsiString> arraylike)
		throws PsiException
	{
		if(arraylike.isEmpty())
			return new PsiString();
		PsiString result=((PsiString)arraylike.get(0)).psiClone();
		for(int i=1; i<arraylike.length(); i++)
		{
			result.psiAppendAll(this);
			result.psiAppendAll(arraylike.get(i));
		}
		return result;
	}
	*/

	/*
	public PsiDict psiSearch(PsiRegExp regexp)
	{
		PsiDict result=null;
		java.util.regex.Pattern pattern=regexp.getPattern();
		java.util.regex.Matcher matcher=pattern.matcher(buffer);
		matcher.find();
		return result;
	}
	*/

	/*public PsiMatcher PsiMatches(PsiRegExp regexp)
	{

	}*/

	@Override
	public void psiSetLength(final PsiInteger oLength)
		throws PsiException
	{
		final long length=oLength.longValue();
		if(length>Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		try
		{
			buffer.setLength((int)length);
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void psiClear()
	{
		buffer.delete(0, buffer.length());
	}

	@Override
	public PsiString psiReverse()
	{
		return new PsiString((new StringBuilder(buffer)).reverse());
	}

	@Override
	public PsiString psiUpperCase()
	{
		return new PsiString(buffer.toString().toUpperCase());
	}

	@Override
	public PsiString psiLowerCase()
	{
		return new PsiString(buffer.toString().toLowerCase());
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
				&& psiEq((PsiString)object).booleanValue();
	}

	@Override
	public int hashCode()
	{
		return buffer.hashCode();
	}

	@Override
	public String toSyntaxString()
	{
		final StringBuilder sb=new StringBuilder();
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
