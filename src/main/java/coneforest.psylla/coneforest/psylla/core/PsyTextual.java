package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	A representation of {@code textual}, a basic type of mutable and immutable
*	strings.
*/
@Type("textual")
public interface PsyTextual
	extends
		PsyEvaluable,
		PsyConvertableToInteger,
		PsyConvertableToIntegral,
		PsyConvertableToReal,
		PsyIterable<PsyInteger>,
		PsyLengthy,
		PsyScalar<PsyTextual>
{

	/**
	*	Returns a string value of this object.
	*
	*	@return a string value.
	*/
	public String stringValue();

	public PsyTextual psyUpperCase();

	public PsyTextual psyLowerCase();

	@Override
	default public PsyName psyToName()
	{
		return new PsyName(stringValue());
	}

	@Override
	default public void psyEval()
		throws PsyErrorException
	{
		(new PsyStringReader(this)).psyEval();
	}

	@Override
	default public PsyInteger psyToInteger()
		throws PsySyntaxErrorException
	{
		try
		{
			// TODO fractional
			return PsyInteger.of(Long.parseLong(stringValue()));
		}
		catch(final NumberFormatException ex)
		{
			throw new PsySyntaxErrorException();
		}
	}

	@Override
	default public PsyIntegral psyToIntegral()
		throws PsySyntaxErrorException
	{
		try
		{
			return PsyIntegral.parseLiteral(stringValue());
		}
		catch(final NumberFormatException ex)
		{
			throw new PsySyntaxErrorException();
		}
	}

	@Override
	default public PsyReal psyToReal()
		throws PsySyntaxErrorException
	{
		try
		{
			return new PsyReal(Double.parseDouble(stringValue()));
		}
		catch(final NumberFormatException ex)
		{
			throw new PsySyntaxErrorException();
		}
	}

	@Override
	default public int length()
	{
		return stringValue().length();
	}

	@Override
	default public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.of(o instanceof PsyTextual
				&& stringValue().equals(((PsyTextual)o).stringValue()));
	}

	@Override
	default public PsyBoolean psyLt(final PsyTextual oString)
	{
		return PsyBoolean.of(stringValue().compareTo(oString.stringValue())<0);
	}

	@Override
	default public PsyBoolean psyLe(final PsyTextual oString)
	{
		return PsyBoolean.of(stringValue().compareTo(oString.stringValue())<=0);
	}

	@Override
	default public PsyBoolean psyGt(final PsyTextual oString)
	{
		return PsyBoolean.of(stringValue().compareTo(oString.stringValue())>0);
	}

	@Override
	default public PsyBoolean psyGe(final PsyTextual oString)
	{
		return PsyBoolean.of(stringValue().compareTo(oString.stringValue())>=0);
	}

	@Override
	default public PsyInteger psyCmp(final PsyTextual oString)
	{
		return PsyInteger.of(stringValue().compareTo(oString.stringValue()));
	}

	default public PsyArray psySplit(final PsyRegExp oRegExp)
		throws PsyLimitCheckException
	{
		final var oArray=new PsyArray();
		for(final var item: oRegExp.getPattern().split(stringValue(), -1))
			// TODO PsyString
			oArray.psyAppend(new PsyString(item));
		return oArray;
	}

	default public PsyInteger psyIndexOfChar(final PsyInteger oChar, final PsyInteger oFrom)
	{
		return PsyInteger.of(stringValue().indexOf(oChar.intValue(), oFrom.intValue()));
	}

	default public PsyInteger psyIndexOfSubstring(final PsyTextual oStr, final PsyInteger oFrom)
	{
		return PsyInteger.of(stringValue().indexOf(oStr.stringValue(), oFrom.intValue()));
	}

	@Override
	default public java.util.Iterator<PsyInteger> iterator()
	{
		return new java.util.Iterator<PsyInteger>()
			{
				public boolean hasNext()
				{
					return index<stringValue().length();
				}

				public PsyInteger next()
				{
					return PsyInteger.of(stringValue().charAt(index++));
				}

				private int index=0;
			};
	}

	/**
	*	Context action of the {@code indexofchar} operator.
	*/
	@OperatorType("indexofchar")
	public static final ContextAction PSY_INDEXOFCHAR
		=ContextAction.<PsyTextual, PsyInteger, PsyInteger>ofTriFunction(PsyTextual::psyIndexOfChar);

	/**
	*	Context action of the {@code indexofsubstring} operator.
	*/
	@OperatorType("indexofsubstring")
	public static final ContextAction PSY_INDEXOFSUBSTRING
		=ContextAction.<PsyTextual, PsyTextual, PsyInteger>ofTriFunction(PsyTextual::psyIndexOfSubstring);

	/**
	*	Context action of the {@code lowercase} operator.
	*/
	@OperatorType("lowercase")
	public static final ContextAction PSY_LOWERCASE
		=ContextAction.<PsyTextual>ofFunction(PsyTextual::psyLowerCase);

	/**
	*	Context action of the {@code split} operator.
	*/
	@OperatorType("split")
	public static final ContextAction PSY_SPLIT
		=ContextAction.<PsyTextual, PsyRegExp>ofBiFunction(PsyTextual::psySplit);

	/**
	*	Context action of the {@code uppercase} operator.
	*/
	@OperatorType("uppercase")
	public static final ContextAction PSY_UPPERCASE
		=ContextAction.<PsyTextual>ofFunction(PsyTextual::psyUpperCase);
}
