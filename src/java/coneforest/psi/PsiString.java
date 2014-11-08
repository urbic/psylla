package coneforest.psi;

/**
 *	A representation of Ψ string object.
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
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(isExecutable())
		{
			interpreter.interpretBraced(new PsiStringReader(this));
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
	public void psiPutInterval(PsiInteger index, PsiIterable<? extends PsiInteger> iterable)
		throws PsiException
	{
		int indexValue=index.intValue();
		if(indexValue<0
				||
				iterable instanceof PsiLengthy
				&& indexValue+((PsiLengthy<PsiInteger>)iterable).length()>=length())
			throw new PsiException("rangecheck");
		for(PsiInteger character: iterable)
		{
			buffer.setCharAt(indexValue++, (char)character.intValue());
			if(indexValue==length())
				break;
		}
	}

	@Override
	public void psiAppend(PsiInteger character)
	{
		buffer.append((char)character.intValue());
	}

	@Override
	public void psiAppendAll(PsiIterable<? extends PsiInteger> iterable)
	{
		for(PsiInteger character: iterable)
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
