package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("matcher")
public class PsyMatcher
	implements PsyResetable
{
	public PsyMatcher(final PsyTextual oTextual, final PsyRegExp oRegExp)
	{
		matcher=oRegExp.getPattern().matcher(oTextual.stringValue());
	}

	public void psyReset()
	{
		matcher.reset();
	}

	public PsyBoolean psyMatches()
	{
		return PsyBoolean.of(matcher.matches());
	}

	public PsyBoolean psyFind()
	{
		return PsyBoolean.of(matcher.find());
	}

	public PsyName psyReplaceAll(final PsyTextual oReplacement)
	{
		return new PsyName(matcher.replaceAll(oReplacement.stringValue()));
	}

	public PsyInteger psyCaptureGroupCount()
	{
		return PsyInteger.of(matcher.groupCount());
	}

	public PsyInteger psyCaptureGroupStart(final PsyObject oKey)
		throws PsyErrorException
	{
		try
		{
			final int start;
			if(oKey instanceof PsyInteger)
				start=matcher.start(((PsyInteger)oKey).intValue());
			else if(oKey instanceof PsyTextual)
				start=matcher.start(((PsyTextual)oKey).stringValue());
			else
				throw new PsyTypeCheckException();
			return start>=0? PsyInteger.of(start): null;
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyUndefinedException();
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	public PsyInteger psyCaptureGroupEnd(final PsyObject oKey)
		throws PsyErrorException
	{
		try
		{
			final int end;
			if(oKey instanceof PsyInteger)
				end=matcher.start(((PsyInteger)oKey).intValue());
			else if(oKey instanceof PsyTextual)
				end=matcher.start(((PsyTextual)oKey).stringValue());
			else
				throw new PsyTypeCheckException();
			return end>=0? PsyInteger.of(end): null;
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyUndefinedException();
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	private final java.util.regex.Matcher matcher;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Action
				("capturegroup", (oContext)->
					{
						final var ostack=oContext.operandStackBacked(2);
						try
						{
							final var matcher=((PsyMatcher)ostack.getBacked(0)).matcher;
							final var oKey=ostack.getBacked(1);
							if(oKey instanceof PsyInteger)
							{
								final var index=((PsyInteger)oKey).intValue();
								final var group=matcher.group(index);
								if(group!=null)
								{
									ostack.push(new PsyName(group));
									ostack.push(PsyInteger.of(matcher.start(index)));
									ostack.push(PsyInteger.of(matcher.end(index)));
								}
								ostack.push(PsyBoolean.of(group!=null));
							}
							else if(oKey instanceof PsyTextual)
							{
								final var key=((PsyTextual)oKey).stringValue();
								final var group=matcher.group(key);
								if(group!=null)
								{
									ostack.push(new PsyName(group));
									ostack.push(PsyInteger.of(matcher.start(key)));
									ostack.push(PsyInteger.of(matcher.end(key)));
								}
								ostack.push(PsyBoolean.of(group!=null));
							}
							else
								throw new PsyTypeCheckException();
						}
						catch(final IndexOutOfBoundsException ex)
						{
							throw new PsyRangeCheckException();
						}
						catch(final IllegalArgumentException ex)
						{
							throw new PsyUndefinedException();
						}
						catch(final IllegalStateException ex)
						{
							throw new PsyInvalidStateException();
						}
					}),
			new PsyOperator.Arity11<PsyMatcher>("capturegroupcount", PsyMatcher::psyCaptureGroupCount),
			new PsyOperator.Arity21<PsyTextual, PsyRegExp>("matcher", PsyMatcher::new),
			new PsyOperator.Arity11<PsyMatcher>("matches", PsyMatcher::psyMatches),
			new PsyOperator.Arity21<PsyMatcher, PsyTextual>("replaceall", PsyMatcher::psyReplaceAll),
		};
}
