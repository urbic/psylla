package coneforest.psi;

/**
*	A representation of Ψ-{@code stringy}, a basic type of mutable and
*	immutable strings.
*/
public interface PsiStringy
	extends
		PsiEvaluable,
		PsiConvertableToInteger,
		PsiConvertableToReal,
		PsiIterable<PsiInteger>,
		PsiLengthy,
		PsiScalar<PsiStringy>
{
	@Override
	default public String getTypeName()
	{
		return "stringy";
	}

	/**
	*	Returns a string value of this object.
	*
	*	@return a string value.
	*/
	public String stringValue();

	public PsiStringy psiUpperCase();

	public PsiStringy psiLowerCase();

	@Override
	default public PsiName psiToName()
	{
		return new PsiName(stringValue());
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
			return PsiInteger.valueOf(Long.parseLong(stringValue()));
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
			return new PsiReal(Double.parseDouble(stringValue()));
		}
		catch(NumberFormatException e)
		{
			throw new PsiSyntaxErrorException();
		}
	}

	@Override
	default public int length()
	{
		return stringValue().length();
	}

	@Override
	default public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(obj instanceof PsiStringy
				&& stringValue().equals(((PsiStringy)obj).stringValue()));
	}

	@Override
	default public PsiBoolean psiLt(final PsiStringy string)
	{
		return PsiBoolean.valueOf(stringValue().compareTo(string.stringValue())<0);
	}

	@Override
	default public PsiBoolean psiLe(final PsiStringy string)
	{
		return PsiBoolean.valueOf(stringValue().compareTo(string.stringValue())<=0);
	}

	@Override
	default public PsiBoolean psiGt(final PsiStringy string)
	{
		return PsiBoolean.valueOf(stringValue().compareTo(string.stringValue())>0);
	}

	@Override
	default public PsiBoolean psiGe(final PsiStringy string)
	{
		return PsiBoolean.valueOf(stringValue().compareTo(string.stringValue())>=0);
	}

	@Override
	default public PsiInteger psiCmp(final PsiStringy stringy)
	{
		return PsiInteger.valueOf(stringValue().compareTo(stringy.stringValue()));
	}

	@Override
	default public java.util.Iterator<PsiInteger> iterator()
	{
		return new java.util.Iterator<PsiInteger>()
			{
				public boolean hasNext()
				{
					return index<stringValue().length();
				}

				public PsiInteger next()
				{
					return PsiInteger.valueOf(stringValue().charAt(index++));
				}

				private int index=0;
			};
	}

}
