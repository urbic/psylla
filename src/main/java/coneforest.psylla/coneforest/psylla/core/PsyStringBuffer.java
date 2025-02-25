package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	An implementation of {@code stringbuffer}.
*/
@Type("stringbuffer")
public class PsyStringBuffer
	implements
		PsyTextual,
		PsyFormalArray<PsyInteger>
{
	/**
	*	Context action of the {@code stringbuffer} operator.
	*/
	@OperatorType("stringbuffer")
	public static final ContextAction PSY_STRING
		=ContextAction.ofSupplier(PsyStringBuffer::new);

	private final StringBuilder buffer;

	/**
	*	Creates a new empty {@code stringbuffer} object.
	*/
	public PsyStringBuffer()
	{
		this("");
	}

	/**
	*	Creates a new {@code stringbuffer} object whose buffer is initialized from string.
	*
	*	@param string a string.
	*/
	public PsyStringBuffer(final String string)
	{
		this(new StringBuilder(string));
	}

	/**
	*	Creates a new {@code string} object with the supplied buffer.
	*
	*	@param buffer a buffer.
	*/
	public PsyStringBuffer(final StringBuilder buffer)
	{
		this.buffer=buffer;
	}

	/**
	*	{@return the buffer}
	*/
	public StringBuilder getBuffer()
	{
		return buffer;
	}

	@Override
	public PsyStringBuffer psyToStringBuffer()
	{
		return this;
	}

	@Override
	public String stringValue()
	{
		return buffer.toString();
	}

	@Override
	public PsyStringBuffer psyClone()
	{
		return new PsyStringBuffer(stringValue());
	}

	@Override
	public PsyInteger get(final int index)
		throws PsyRangeCheckException
	{
		try
		{
			return PsyInteger.of(buffer.charAt(index));
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyStringBuffer psyGetInterval(final PsyInteger oIndex, final PsyInteger oCount)
		throws PsyRangeCheckException
	{
		final int index=oIndex.intValue();
		final int count=oCount.intValue();
		try
		{
			return new PsyStringBuffer(buffer.substring(index, index+count));
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void put(final int index, final PsyInteger oCharacter)
		throws PsyRangeCheckException
	{
		try
		{
			buffer.setCharAt(index, (char)oCharacter.intValue());
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void psyPutInterval(final PsyInteger oIndex, final PsyIterable<? extends PsyInteger> oIterable)
		throws PsyRangeCheckException
	{
		int index=oIndex.intValue();
		if(index<0
				||
				oIterable instanceof PsyLengthy oLengthy
				&& index+oLengthy.length()>=length())
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
		throws PsyRangeCheckException
	{
		try
		{
			buffer.insert(index, (char)oCharacter.intValue());
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void psyInsertAll(final PsyInteger oIndex, final PsyIterable<? extends PsyInteger> oIterable)
		throws PsyRangeCheckException
	{
		int index=oIndex.intValue();
		try
		{
			if(oIterable instanceof PsyStringBuffer oString)
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
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void delete(final int index)
		throws PsyRangeCheckException
	{
		try
		{
			buffer.deleteCharAt(index);
		}
		catch(final StringIndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyInteger extract(final int index)
		throws PsyRangeCheckException
	{
		try
		{
			final PsyInteger oResult=get(index);
			buffer.deleteCharAt(index);
			return oResult;
		}
		catch(final StringIndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyStringBuffer psyExtractInterval(final PsyInteger oStart, final PsyInteger oLength)
		throws PsyRangeCheckException
	{
		final var oResult=psyGetInterval(oStart, oLength);
		buffer.replace(oStart.intValue(), oStart.intValue()+oLength.intValue(), "");
		return oResult;
	}

	@Override
	public PsyStringBuffer psySlice(final PsyIterable<PsyInteger> oIndices)
		throws PsyRangeCheckException, PsyLimitCheckException
	{
		final var oValues=new PsyStringBuffer();
		for(final var oIndex: oIndices)
			oValues.psyAppend(psyGet(oIndex));
		return oValues;
	}

	/*
	public PsyStringBuffer psyJoin(final PsyFormalArray<? extends PsyStringBuffer> oArray)
		throws PsyErrorException
	{
		if(oArray.isEmpty())
			return new PsyStringBuffer();
		PsyStringBuffer result=((PsyStringBuffer)oArray.get(0)).psyClone();
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
		Pattern pattern=regexp.getPattern();
		Matcher matcher=pattern.matcher(buffer);
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
		throws PsyLimitCheckException, PsyRangeCheckException
	{
		final long length=oLength.longValue();
		if(length>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		try
		{
			buffer.setLength((int)length);
		}
		catch(final IndexOutOfBoundsException ex)
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
	public PsyStringBuffer psyReverse()
	{
		return new PsyStringBuffer((new StringBuilder(buffer)).reverse());
	}

	@Override
	public PsyStringBuffer psyUpperCase()
	{
		return new PsyStringBuffer(buffer.toString().toUpperCase());
	}

	@Override
	public PsyStringBuffer psyLowerCase()
	{
		return new PsyStringBuffer(buffer.toString().toLowerCase());
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
				&& psyEq((PsyStringBuffer)object).booleanValue();
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
			sb.append(switch(c)
				{
					case '\u0000'->"\\0";
					case '\u0007'->"\\a";
					case '\n'->"\\n";
					case '\r'->"\\r";
					case '\t'->"\\t";
					case '\u000B'->"\\v";
					case '\f'->"\\f";
					case '\u001B'->"\\e";
					case '\"'->"\\\"";
					case '\\'->"\\\\";
					default->c;
				});
		}
		return "\""+sb.toString()+"\"";
	}

	/**
	*	{@return the {@code string} object obtained as a result of parsing the literal token image}
	*
	*	@param image the literal token image.
	*	@throws PsySyntaxErrorException when the literal image is syntactically incorrect.
	*/
	public static PsyStringBuffer parseLiteral(final String image)
		throws PsySyntaxErrorException
	{
		final var sb=new StringBuilder();
		for(int i=1; i<image.length()-1; i++)
		{
			final var c=image.charAt(i);
			switch(c)
			{
				case '\\'->
					{
						i++;
						switch(image.charAt(i))
						{
							case '0'->sb.append('\u0000');
							case 'a'->sb.append('\u0007');
							case 'n'->sb.append('\n');
							case 'r'->sb.append('\r');
							case 't'->sb.append('\t');
							case 'v'->sb.append('\u000B');
							case 'f'->sb.append('\f');
							case 'e'->sb.append('\u001B');
							case '"'->sb.append('"');
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
					}
				default->sb.append(c);
			}
		}
		return new PsyStringBuffer(sb);
	}
}
