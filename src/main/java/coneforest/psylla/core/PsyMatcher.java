package coneforest.psylla.core;

@coneforest.psylla.Type("matcher")
public class PsyMatcher
	implements PsyResettable
{
	public PsyMatcher(final PsyStringy oStringy, final PsyRegExp oRegExp)
	{
		matcher=oRegExp.getPattern().matcher(oStringy.stringValue());
	}

	public void psyReset()
	{
		matcher.reset();
	}

	public PsyBoolean psyMatches()
	{
		return PsyBoolean.valueOf(matcher.matches());
	}

	public PsyBoolean psyFind()
	{
		return PsyBoolean.valueOf(matcher.find());
	}

	public PsyName psyReplaceAll(final PsyStringy oReplacement)
	{
		return new PsyName(matcher.replaceAll(oReplacement.stringValue()));
	}

	public PsyInteger psyCaptureGroupCount()
	{
		return PsyInteger.valueOf(matcher.groupCount());
	}

	public PsyString psyCaptureGroup(final PsyObject oKey)
		throws PsyException
	{
		try
		{
			final String group;
			if(oKey instanceof PsyInteger)
				group=matcher.group(((PsyInteger)oKey).intValue());
			else if(oKey instanceof PsyStringy)
				group=matcher.group(((PsyStringy)oKey).stringValue());
			else
				throw new PsyTypeCheckException();
			return group!=null? new PsyString(group): null;
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsyUndefinedException();
		}
		catch(final IllegalStateException e)
		{
			throw new PsyInvalidStateException();
		}
	}

	public PsyInteger psyCaptureGroupStart(final PsyObject oKey)
		throws PsyException
	{
		try
		{
			final int start;
			if(oKey instanceof PsyInteger)
				start=matcher.start(((PsyInteger)oKey).intValue());
			else if(oKey instanceof PsyStringy)
				start=matcher.start(((PsyStringy)oKey).stringValue());
			else
				throw new PsyTypeCheckException();
			return start>=0? PsyInteger.valueOf(start): null;
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsyUndefinedException();
		}
		catch(final IllegalStateException e)
		{
			throw new PsyInvalidStateException();
		}
	}

	public PsyInteger psyCaptureGroupEnd(final PsyObject oKey)
		throws PsyException
	{
		try
		{
			final int end;
			if(oKey instanceof PsyInteger)
				end=matcher.start(((PsyInteger)oKey).intValue());
			else if(oKey instanceof PsyStringy)
				end=matcher.start(((PsyStringy)oKey).stringValue());
			else
				throw new PsyTypeCheckException();
			return end>=0? PsyInteger.valueOf(end): null;
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsyUndefinedException();
		}
		catch(final IllegalStateException e)
		{
			throw new PsyInvalidStateException();
		}
	}

	private final java.util.regex.Matcher matcher;
}
