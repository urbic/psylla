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

	public String getTypeName() { return "string"; }

	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
		// TODO: executable strings
	}

	@Override
	public String getString()
	{
		return buffer.toString();
	}

	public void setValue(final String value)
	{
		buffer.replace(0, value.length(), value);
	}

	public StringBuilder getBuffer()
	{
		return buffer;
	}

	private PsiInteger get(int index)
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
	public PsiInteger psiGet(PsiInteger index)
		throws PsiException
	{
		return get(index.getValue().intValue());
	}

	private void put(int index, PsiInteger oChar)
		throws PsiException
	{
		try
		{
			buffer.setCharAt(index, (char)oChar.getValue().intValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiPut(PsiInteger index, PsiInteger character)
		throws PsiException
	{
		put(index.getValue().intValue(), character);
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
	public void psiInsert(PsiInteger index, PsiInteger character)
		throws PsiException
	{
		try
		{
			buffer.insert(index.getValue().intValue(), (char)character.getValue().intValue());
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

	/*
	public static PsiString concatenation(PsiString x, PsiString y)
	{
		return new PsiString(x.getValue()+y.getValue());
	}
	*/

	@Override
	public PsiBoolean psiLt(final PsiString string)
	{
		return new PsiBoolean(getString().compareTo(string.getString())<0);
	}

	@Override
	public PsiBoolean psiLe(final PsiString string)
	{
		return new PsiBoolean(getString().compareTo(string.getString())<=0);
	}

	@Override
	public PsiBoolean psiGt(final PsiString string)
	{
		return new PsiBoolean(getString().compareTo(string.getString())>0);
	}

	@Override
	public PsiBoolean psiGe(final PsiString string)
	{
		return new PsiBoolean(getString().compareTo(string.getString())>=0);
	}

	private StringBuilder buffer;
}
