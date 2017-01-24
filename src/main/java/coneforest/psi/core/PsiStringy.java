package coneforest.psi.core;
import coneforest.psi.*;

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
	default public String typeName()
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
	default public void eval(final Interpreter interpreter)
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
		catch(final NumberFormatException e)
		{
			throw new PsiSyntaxErrorException();
		}
	}

	@Override
	default public PsiReal psiToReal()
		throws PsiSyntaxErrorException
	{
		try
		{
			return new PsiReal(Double.parseDouble(stringValue()));
		}
		catch(final NumberFormatException e)
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
	default public PsiBoolean psiEq(final PsiObject o)
	{
		return PsiBoolean.valueOf(o instanceof PsiStringy
				&& stringValue().equals(((PsiStringy)o).stringValue()));
	}

	@Override
	default public PsiBoolean psiLt(final PsiStringy oString)
	{
		return PsiBoolean.valueOf(stringValue().compareTo(oString.stringValue())<0);
	}

	@Override
	default public PsiBoolean psiLe(final PsiStringy oString)
	{
		return PsiBoolean.valueOf(stringValue().compareTo(oString.stringValue())<=0);
	}

	@Override
	default public PsiBoolean psiGt(final PsiStringy oString)
	{
		return PsiBoolean.valueOf(stringValue().compareTo(oString.stringValue())>0);
	}

	@Override
	default public PsiBoolean psiGe(final PsiStringy oString)
	{
		return PsiBoolean.valueOf(stringValue().compareTo(oString.stringValue())>=0);
	}

	@Override
	default public PsiInteger psiCmp(final PsiStringy oString)
	{
		return PsiInteger.valueOf(stringValue().compareTo(oString.stringValue()));
	}

	default public PsiArray psiSplit(final PsiRegExp oRegExp)
		throws PsiException
	{
		PsiArray oArray=new PsiArray();
		for(String item: oRegExp.getPattern().split(stringValue(), -1))
			oArray.psiAppend(new PsiString(item));
		return oArray;
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
