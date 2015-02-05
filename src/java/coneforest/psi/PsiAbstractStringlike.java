package coneforest.psi;

abstract public class PsiAbstractStringlike
	extends PsiAbstractObject
	implements
		PsiStringlike
{
	@Override
	public PsiAbstractStringlike psiCloneEmpty()
		throws PsiException
	{
		try
		{
			return getClass().newInstance();
		}
		catch(InstantiationException|IllegalAccessException e)
		{
			throw new PsiException("unknownerror");
		}
	}

	@Override
	public PsiName psiToName()
	{
		return new PsiName(getString());
	}

	@Override
	public void eval(Interpreter interpreter)
		throws PsiException
	{
		(new PsiStringReader(this)).eval(interpreter);
	}

	@Override
	public PsiInteger psiToInteger()
		throws PsiException
	{
		try
		{
			// TODO fractional
			return new PsiInteger(Long.parseLong(getString()));
		}
		catch(NumberFormatException e)
		{
			throw new PsiException("syntaxerror");
		}
	}

	@Override
	public PsiReal psiToReal()
		throws PsiException
	{
		try
		{
			// TODO fractional
			return new PsiReal(Double.parseDouble(getString()));
		}
		catch(NumberFormatException e)
		{
			// TODO maybe "syntaxerror"?
			throw new PsiException("typecheck");
		}
	}

	@Override
	public int length()
	{
		return getString().length();
	}

	@Override
	public boolean isEmpty()
	{
		return length()==0;
	}

	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(getString().length());
	}

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(obj instanceof PsiStringlike
				&& getString().equals(((PsiStringlike)obj).getString()));
	}

	@Override
	public PsiBoolean psiLt(final PsiStringlike string)
	{
		return PsiBoolean.valueOf(getString().compareTo(string.getString())<0);
	}

	@Override
	public PsiBoolean psiLe(final PsiStringlike string)
	{
		return PsiBoolean.valueOf(getString().compareTo(string.getString())<=0);
	}

	@Override
	public PsiBoolean psiGt(final PsiStringlike string)
	{
		return PsiBoolean.valueOf(getString().compareTo(string.getString())>0);
	}

	@Override
	public PsiBoolean psiGe(final PsiStringlike string)
	{
		return PsiBoolean.valueOf(getString().compareTo(string.getString())>=0);
	}

	@Override
	public PsiInteger psiCmp(final PsiStringlike stringlike)
	{
		return new PsiInteger(getString().compareTo(stringlike.getString()));
	}

	/*
	@Override
	public PsiAbstractStringlike psiReplicate(PsiInteger count)
		throws PsiException
	{
		int countValue=count.intValue();
		if(countValue<0)
			throw new PsiException("rangecheck");
		PsiAbstractStringlike result=psiCloneEmpty();
		while(countValue-->0)
			result.psiAppendAll(this);
		return result;
	}
	*/

	@Override
	public java.util.Iterator<PsiInteger> iterator()
	{
		return new java.util.Iterator<PsiInteger>()
			{
				public boolean hasNext()
				{
					return index<getString().length();
				}

				public PsiInteger next()
				{
					return new PsiInteger(getString().charAt(index++));
				}

				public void remove()
				{
					throw new UnsupportedOperationException();
				}

				private int index=0;
			};
	}

	@Override
	public String toStringHelper(PsiLengthy lengthy)
	{
		return toString();
	}

	@Override
	public int hashCode()
	{
		return getString().hashCode();
	}
}
