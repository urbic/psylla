package coneforest.psi;

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

	public void invoke(Interpreter interpreter)
	{
		if(isExecutable())
			interpreter.interpret(new PsiStringReader(this));
		else
			super.execute(interpreter);
	}

	@Override
	public String getString()
	{
		return buffer.toString();
	}

	/*
	public void setValue(final String value)
	{
		buffer.replace(0, value.length(), value);
	}
	*/

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
	public void psiPut(int index, PsiInteger character)
		throws PsiException
	{
		try
		{
			buffer.setCharAt(index, (char)character.getValue().intValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiAppend(PsiInteger character)
	{
		buffer.append((char)character.getValue().intValue());
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
			buffer.insert(indexValue, (char)character.getValue().intValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	public void psiInsertAll(PsiInteger index, PsiString string)
		throws PsiException
	{
		int indexValue=index.getValue().intValue();
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
		int indexValue=index.getValue().intValue();
		try
		{
			for(PsiInteger character: iterable)
				buffer.insert(indexValue++, (char)character.getValue().intValue());
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
	public boolean equals(Object object)
	{
		return object instanceof PsiString
				&& psiEq((PsiString)object).getValue();
	}

	@Override
	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		String value=getString();
		for(int i=0; i<value.length(); i++)
		{
			char c=value.charAt(i);
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

	private StringBuilder buffer;
}
