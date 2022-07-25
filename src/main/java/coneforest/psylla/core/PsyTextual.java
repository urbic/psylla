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
		throws PsyException
	{
		(new PsyStringReader(this)).psyEval();
	}

	@Override
	default public PsyInteger psyToInteger()
		throws PsyException
	{
		try
		{
			// TODO fractional
			return PsyInteger.valueOf(Long.parseLong(stringValue()));
		}
		catch(final NumberFormatException e)
		{
			throw new PsySyntaxErrorException();
		}
	}

	@Override
	default public PsyIntegral psyToIntegral()
		throws PsyException
	{
		try
		{
			return PsyIntegral.parse(stringValue());
		}
		catch(final NumberFormatException e)
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
		catch(final NumberFormatException e)
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
		return PsyBoolean.valueOf(o instanceof PsyTextual
				&& stringValue().equals(((PsyTextual)o).stringValue()));
	}

	@Override
	default public PsyBoolean psyLt(final PsyTextual oString)
	{
		return PsyBoolean.valueOf(stringValue().compareTo(oString.stringValue())<0);
	}

	@Override
	default public PsyBoolean psyLe(final PsyTextual oString)
	{
		return PsyBoolean.valueOf(stringValue().compareTo(oString.stringValue())<=0);
	}

	@Override
	default public PsyBoolean psyGt(final PsyTextual oString)
	{
		return PsyBoolean.valueOf(stringValue().compareTo(oString.stringValue())>0);
	}

	@Override
	default public PsyBoolean psyGe(final PsyTextual oString)
	{
		return PsyBoolean.valueOf(stringValue().compareTo(oString.stringValue())>=0);
	}

	@Override
	default public PsyInteger psyCmp(final PsyTextual oString)
	{
		return PsyInteger.valueOf(stringValue().compareTo(oString.stringValue()));
	}

	default public PsyArray psySplit(final PsyRegExp oRegExp)
		throws PsyException
	{
		final var oArray=new PsyArray();
		for(final var item: oRegExp.getPattern().split(stringValue(), -1))
			// TODO PsyString
			oArray.psyAppend(new PsyString(item));
		return oArray;
	}

	default public PsyInteger psyIndexOfChar(final PsyInteger oChar, final PsyInteger oFrom)
	{
		return PsyInteger.valueOf(stringValue().indexOf(oChar.intValue(), oFrom.intValue()));
	}

	default public PsyInteger psyIndexOfSubstring(final PsyTextual oStr, final PsyInteger oFrom)
	{
		return PsyInteger.valueOf(stringValue().indexOf(oStr.stringValue(), oFrom.intValue()));
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
					return PsyInteger.valueOf(stringValue().charAt(index++));
				}

				private int index=0;
			};
	}

	/*@Override
	default public java.util.stream.Stream<PsyInteger> stream()
	{
		return stringValue().chars().<PsyInteger>mapToObj(PsyInteger::valueOf);
	}*/

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity31<PsyTextual, PsyInteger, PsyInteger>
				("indexofchar", PsyTextual::psyIndexOfChar),
			new PsyOperator.Arity31<PsyTextual, PsyTextual, PsyInteger>
				("indexofsubstring", PsyTextual::psyIndexOfSubstring),
			new PsyOperator.Arity11<PsyTextual>
				("lowercase", PsyTextual::psyLowerCase),
			new PsyOperator.Arity21<PsyTextual, PsyRegExp>
				("split", PsyTextual::psySplit),
			new PsyOperator.Arity11<PsyTextual>
				("uppercase", PsyTextual::psyUpperCase),
		};

}
