package coneforest.psi;

public interface PsiStringlike
	extends
		PsiContainer<PsiInteger>,
		PsiScalar<PsiStringlike>,
		PsiEvaluable,
		PsiConvertableToInteger,
		PsiConvertableToReal
{
	public String getString();

	public PsiStringlike psiUpperCase();
	
	public PsiStringlike psiLowerCase();

	@Override
	default public PsiName psiToName()
	{
		return new PsiName(getString());
	}

	@Override
	default public void eval(Interpreter interpreter)
		throws PsiException
	{
		(new PsiStringReader(this)).eval(interpreter);
	}

	@Override
	default public PsiInteger psiToInteger()
		throws PsiException
	{
		try
		{
			// TODO fractional
			return PsiInteger.valueOf(Long.parseLong(getString()));
		}
		catch(NumberFormatException e)
		{
			throw new PsiException("syntaxerror");
		}
	}

	@Override
	default public PsiReal psiToReal()
		throws PsiException
	{
		try
		{
			return new PsiReal(Double.parseDouble(getString()));
		}
		catch(NumberFormatException e)
		{
			throw new PsiException("syntaxerror");
		}
	}

	@Override
	default public int length()
	{
		return getString().length();
	}

	@Override
	default public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(obj instanceof PsiStringlike
				&& getString().equals(((PsiStringlike)obj).getString()));
	}

	@Override
	default public PsiBoolean psiLt(final PsiStringlike string)
	{
		return PsiBoolean.valueOf(getString().compareTo(string.getString())<0);
	}

	@Override
	default public PsiBoolean psiLe(final PsiStringlike string)
	{
		return PsiBoolean.valueOf(getString().compareTo(string.getString())<=0);
	}

	@Override
	default public PsiBoolean psiGt(final PsiStringlike string)
	{
		return PsiBoolean.valueOf(getString().compareTo(string.getString())>0);
	}

	@Override
	default public PsiBoolean psiGe(final PsiStringlike string)
	{
		return PsiBoolean.valueOf(getString().compareTo(string.getString())>=0);
	}

	@Override
	default public PsiInteger psiCmp(final PsiStringlike stringlike)
	{
		return PsiInteger.valueOf(getString().compareTo(stringlike.getString()));
	}

	@Override
	default public java.util.Iterator<PsiInteger> iterator()
	{
		return new java.util.Iterator<PsiInteger>()
			{
				public boolean hasNext()
				{
					return index<getString().length();
				}

				public PsiInteger next()
				{
					return PsiInteger.valueOf(getString().charAt(index++));
				}

				public void remove()
				{
					throw new UnsupportedOperationException();
				}

				private int index=0;
			};
	}

}
