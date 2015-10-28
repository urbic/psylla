package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">string</code> object.
 */
public class PsiString
	implements PsiStringlike
{
	public PsiString()
	{
		this("");
	}

	public PsiString(String string)
	{
		buffer=new StringBuilder(string);
	}

	public PsiString(StringBuilder buffer)
	{
		this.buffer=buffer;
	}

	@Override
	public PsiString psiToString()
	{
		return this;
	}

	@Override
	public String getString()
	{
		return buffer.toString();
	}

	public StringBuilder getBuffer()
	{
		return buffer;
	}

	@Override
	public PsiString psiClone()
	{
		return new PsiString(getString());
	}

	@Override
	public PsiInteger get(int index)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(buffer.charAt(index));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiString psiGetInterval(PsiInteger index, PsiInteger count)
		throws PsiException
	{
		int indexValue=index.intValue();
		int countValue=count.intValue();
		try
		{
			return new PsiString(buffer.substring(indexValue, indexValue+countValue));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void put(int index, PsiInteger character)
		throws PsiException
	{
		try
		{
			buffer.setCharAt(index, (char)character.intValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void psiPutInterval(final PsiInteger index, final PsiIterable<? extends PsiInteger> iterable)
		throws PsiException
	{
		int indexValue=index.intValue();
		if(indexValue<0
				||
				iterable instanceof PsiLengthy
				&& indexValue+((PsiLengthy)iterable).length()>=length())
			throw new PsiRangeCheckException();
		for(PsiInteger character: iterable)
		{
			buffer.setCharAt(indexValue++, (char)character.intValue());
			if(indexValue==length())
				break;
		}
	}

	@Override
	public void psiAppend(final PsiInteger character)
		throws PsiException
	{
		if(length()==Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		buffer.append((char)character.intValue());
	}

	@Override
	public void insert(int indexValue, PsiInteger character)
		throws PsiException
	{
		try
		{
			buffer.insert(indexValue, (char)character.intValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	public void psiInsertAll(PsiInteger index, PsiString string)
		throws PsiException
	{
		int indexValue=index.intValue();
		try
		{
			buffer.insert(indexValue, string.buffer);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void psiInsertAll(PsiInteger index, PsiIterable<? extends PsiInteger> iterable)
		throws PsiException
	{
		if(iterable instanceof PsiString)
		{
			psiInsertAll(index, (PsiString)iterable);
			return;
		}
		int indexValue=index.intValue();
		try
		{
			for(PsiInteger character: iterable)
				buffer.insert(indexValue++, (char)character.intValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void delete(int indexValue)
		throws PsiException
	{
		try
		{
			buffer.deleteCharAt(indexValue);
		}
		catch(StringIndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiInteger extract(int indexValue)
		throws PsiException
	{
		try
		{
			PsiInteger result=get(indexValue);
			buffer.deleteCharAt(indexValue);
			return result;
		}
		catch(StringIndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiString psiExtractInterval(PsiInteger start, PsiInteger length)
		throws PsiException
	{
		PsiString result=psiGetInterval(start, length);
		buffer.replace(start.intValue(), start.intValue()+length.intValue(), "");
		return result;
	}

	@Override
	public PsiString psiSlice(PsiIterable<PsiInteger> indices)
		throws PsiException
	{
		PsiString values=new PsiString();
		for(PsiInteger index: indices)
			values.psiAppend(psiGet(index));
		return values;
	}

	public PsiString psiJoin(PsiArraylike<? extends PsiString> arraylike)
		throws PsiException
	{
		if(arraylike.isEmpty())
			return new PsiString("");
		PsiString result=((PsiString)arraylike.get(0)).psiClone();
		for(int i=1; i<arraylike.length(); i++)
		{
			result.psiAppendAll(this);
			result.psiAppendAll(arraylike.get(i));
		}
		return result;
	}

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

	public PsiArray psiSplit(PsiRegExp regexp)
		throws PsiException
	{
		PsiArray array=new PsiArray();
		for(String item: regexp.getPattern().split(getString(), -1))
			array.psiAppend(new PsiString(item));
		return array;
	}

	@Override
	public void psiSetLength(final PsiInteger length)
		throws PsiException
	{
		final long lengthValue=length.longValue();
		if(lengthValue>Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		try
		{
			buffer.setLength((int)lengthValue);
		}
		catch(IndexOutOfBoundsException e)
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
	public boolean equals(Object object)
	{
		return getClass().isInstance(object)
				&& psiEq((PsiString)object).booleanValue();
	}

	@Override
	public int hashCode()
	{
		return buffer.hashCode();
	}

	private StringBuilder buffer;
}
