package coneforest.psi;

abstract public class PsiAbstractStringlike
	extends PsiAbstractObject
	implements
		PsiStringlike
{
	@Override
	public PsiName psiToName()
	{
		return new PsiName(getString());
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
			// TODO maybe "syntaxerror"?
			throw new PsiException("typecheck");
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
		return new PsiBoolean(obj instanceof PsiStringlike
				&& getString().equals(((PsiStringlike)obj).getString()));
	}

	@Override
	public PsiBoolean psiLt(final PsiStringlike string)
	{
		return new PsiBoolean(getString().compareTo(string.getString())<0);
	}

	@Override
	public PsiBoolean psiLe(final PsiStringlike string)
	{
		return new PsiBoolean(getString().compareTo(string.getString())<=0);
	}

	@Override
	public PsiBoolean psiGt(final PsiStringlike string)
	{
		return new PsiBoolean(getString().compareTo(string.getString())>0);
	}

	@Override
	public PsiBoolean psiGe(final PsiStringlike string)
	{
		return new PsiBoolean(getString().compareTo(string.getString())>=0);
	}

	@Override
	public PsiInteger psiCmp(final PsiStringlike stringlike)
	{
		return new PsiInteger(getString().compareTo(stringlike.getString()));
	}

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
