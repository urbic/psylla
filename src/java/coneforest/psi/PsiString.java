package coneforest.psi;

/**
 *	A representation of Ψ <code class="type">string</code> object.
 */
public class PsiString
	extends PsiAbstractString
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
	public PsiAbstractString psiCloneEmpty()
	{
		return new PsiString();
	}

	@Override
	public PsiString psiToString()
	{
		return this;
	}

	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(isExecutable())
		{
			try
			{
				interpreter.interpretBraced(new PsiStringReader(this));
			}
			catch(PsiException e)
			{
				interpreter.error(e, this);
			}
			opstack.pop().invoke(interpreter);
		}
		else
			super.execute(interpreter);
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
	public PsiInteger psiGet(int index)
		throws PsiException
	{
		try
		{
			return new PsiInteger(buffer.charAt(index));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
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
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiPut(int index, PsiInteger character)
		throws PsiException
	{
		try
		{
			buffer.setCharAt(index, (char)character.intValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
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
			throw new PsiException("rangecheck");
		for(PsiInteger character: iterable)
		{
			buffer.setCharAt(indexValue++, (char)character.intValue());
			if(indexValue==length())
				break;
		}
	}

	@Override
	public void psiAppend(final PsiInteger character)
	{
		buffer.append((char)character.intValue());
	}

	@Override
	public void psiAppendAll(final PsiIterable<? extends PsiInteger> iterable)
	{
		for(PsiInteger character:
				(this!=iterable? iterable: (PsiIterable<? extends PsiInteger>)psiClone()))
			psiAppend(character);
	}

	@Override
	public void psiInsert(int indexValue, PsiInteger character)
		throws PsiException
	{
		try
		{
			buffer.insert(indexValue, (char)character.intValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
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
			throw new PsiException("rangecheck");
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
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public PsiInteger psiDelete(int indexValue)
		throws PsiException
	{
		try
		{
			PsiInteger result=psiGet(indexValue);
			buffer.deleteCharAt(indexValue);
			return result;
		}
		catch(StringIndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public PsiString psiDeleteInterval(PsiInteger start, PsiInteger length)
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
		PsiString result=((PsiString)arraylike.psiGet(0)).psiClone();
		for(int i=1; i<arraylike.length(); i++)
		{
			result.psiAppendAll(this);
			result.psiAppendAll(arraylike.psiGet(i));
		}
		return result;
	}

	/*
	public PsiDictionary psiSearch(PsiRegExp regexp)
	{
		PsiDictionary result=null;
		java.util.regex.Pattern pattern=regexp.getPattern();
		java.util.regex.Matcher matcher=pattern.matcher(buffer);
		matcher.find();
		return result;
	}
	*/

	public PsiArray psiSplit(PsiRegExp regexp)
	{
		PsiArray array=new PsiArray();
		for(String item: regexp.getPattern().split(getString(), -1))
			array.psiAppend(new PsiString(item));
		return array;
	}

	@Override
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(buffer.length()==0);
	}

	@Override
	public void psiClear()
	{
		buffer.delete(0, buffer.length());
	}

	@Override
	public void psiReverse()
	{
		buffer.reverse();
	}

	@Override
	public int length()
	{
		return buffer.length();
	}
	private StringBuilder buffer;
}
