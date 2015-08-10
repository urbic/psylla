package coneforest.psi;

public interface PsiStringy
	extends
		PsiContainer<PsiInteger>,
		PsiScalar<PsiStringy>,
		PsiEvaluable,
		PsiConvertableToInteger,
		PsiConvertableToReal
{
	@Override
	default public String getTypeName()
	{
		return "stringy";
	}

	public String getString();

	public PsiStringy psiUpperCase();

	public PsiStringy psiLowerCase();

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
			throw new PsiSyntaxErrorException();
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
			throw new PsiSyntaxErrorException();
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
		return PsiBoolean.valueOf(obj instanceof PsiStringy
				&& getString().equals(((PsiStringy)obj).getString()));
	}

	@Override
	default public PsiBoolean psiLt(final PsiStringy string)
	{
		return PsiBoolean.valueOf(getString().compareTo(string.getString())<0);
	}

	@Override
	default public PsiBoolean psiLe(final PsiStringy string)
	{
		return PsiBoolean.valueOf(getString().compareTo(string.getString())<=0);
	}

	@Override
	default public PsiBoolean psiGt(final PsiStringy string)
	{
		return PsiBoolean.valueOf(getString().compareTo(string.getString())>0);
	}

	@Override
	default public PsiBoolean psiGe(final PsiStringy string)
	{
		return PsiBoolean.valueOf(getString().compareTo(string.getString())>=0);
	}

	@Override
	default public PsiInteger psiCmp(final PsiStringy stringy)
	{
		return PsiInteger.valueOf(getString().compareTo(stringy.getString()));
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

				private int index=0;
			};
	}

}
