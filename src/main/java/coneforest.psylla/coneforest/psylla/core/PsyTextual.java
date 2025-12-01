package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.Iterator;
import java.util.Optional;

/**
*	The representation of {@code textual}, a basic type of mutable and immutable strings.
*/
@Type("textual")
public interface PsyTextual
	extends
		PsyEvaluable,
		PsyConvertableToInteger,
		PsyConvertableToIntegral,
		PsyConvertableToRational,
		PsyConvertableToReal,
		PsyIterable<PsyInteger>,
		PsyLengthy,
		PsyScalar<PsyTextual>
{
	/**
	*	Context action of the {@code indexofchar} operator.
	*/
	@OperatorType("indexofchar")
	public static final ContextAction PSY_INDEXOFCHAR
		=ContextAction.<PsyTextual, PsyInteger, PsyInteger>ofOptionalTriFunction(PsyTextual::psyIndexOfChar);

	/**
	*	Context action of the {@code indexofsubstring} operator.
	*/
	@OperatorType("indexofsubstring")
	public static final ContextAction PSY_INDEXOFSUBSTRING
		=ContextAction.<PsyTextual, PsyTextual, PsyInteger>ofOptionalTriFunction(PsyTextual::psyIndexOfSubstring);

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

	/**
	*	{@return a string value of this object}
	*/
	public String stringValue();

	public PsyTextual psyUpperCase();

	public PsyTextual psyLowerCase();

	@Override
	public default PsyString psyToString()
	{
		return new PsyString(stringValue());
	}

	@Override
	public default void psyEval(final PsyContext oContext)
		throws PsyErrorException
	{
		(new PsyStringReader(stringValue())).psyEval(oContext);
	}

	@Override
	public default PsyInteger psyToInteger()
		throws PsySyntaxErrorException
	{
		try
		{
			// TODO fraction
			return PsyInteger.of(Long.parseLong(stringValue()));
		}
		catch(final NumberFormatException ex)
		{
			throw new PsySyntaxErrorException();
		}
	}

	@Override
	public default PsyIntegral psyToIntegral()
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
	public default PsyRational psyToRational()
		throws PsySyntaxErrorException, PsyUndefinedResultException
	{
		try
		{
			return PsyRational.parseLiteral(stringValue());
		}
		catch(final NumberFormatException ex)
		{
			throw new PsySyntaxErrorException();
		}
	}

	@Override
	public default PsyReal psyToReal()
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
	public default int length()
	{
		return stringValue().length();
	}

	@Override
	public default int compareTo(final PsyTextual oTextual)
	{
		return stringValue().compareTo(oTextual.stringValue());
	}

	@Override
	public default PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.of(o instanceof PsyTextual oTextual
				&& stringValue().equals(oTextual.stringValue()));
	}

	public default PsyArray psySplit(final PsyRegExp oRegExp)
		throws PsyLimitCheckException
	{
		final var oArray=new PsyArray();
		for(final var item: oRegExp.getPattern().split(stringValue(), -1))
			// TODO PsyString
			oArray.psyAppend(new PsyStringBuffer(item));
		return oArray;
	}

	public default Optional<PsyInteger> psyIndexOfChar(final PsyInteger oChar, final PsyInteger oFrom)
	{
		final var index=stringValue().indexOf(oChar.intValue(), oFrom.intValue());
		return index<0? Optional.empty(): Optional.of(PsyInteger.of(index));
	}

	public default Optional<PsyInteger> psyIndexOfSubstring(final PsyTextual oStr, final PsyInteger oFrom)
	{
		final var index=stringValue().indexOf(oStr.stringValue(), oFrom.intValue());
		return index<0? Optional.empty(): Optional.of(PsyInteger.of(index));
	}

	@Override
	public default Iterator<PsyInteger> iterator()
	{
		return new Iterator<PsyInteger>()
			{
				private int index=0;

				@Override
				public boolean hasNext()
				{
					return index<stringValue().length();
				}

				@Override
				public PsyInteger next()
				{
					return PsyInteger.of(stringValue().charAt(index++));
				}
			};
	}

}
