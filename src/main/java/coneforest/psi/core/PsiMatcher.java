package coneforest.psi.core;

public class PsiMatcher
	implements PsiResettable
{
	public PsiMatcher(final PsiStringy oStringy, final PsiRegExp oRegExp)
	{
		matcher=oRegExp.getPattern().matcher(oStringy.stringValue());
	}

	public void psiReset()
	{
		matcher.reset();
	}

	public PsiBoolean psiMatches()
	{
		return PsiBoolean.valueOf(matcher.matches());
	}

	public PsiBoolean psiFind()
	{
		return PsiBoolean.valueOf(matcher.find());
	}

	public PsiName psiReplaceAll(final PsiStringy oReplacement)
	{
		return new PsiName(matcher.replaceAll(oReplacement.stringValue()));
	}

	public PsiInteger psiCaptureGroupCount()
	{
		return PsiInteger.valueOf(matcher.groupCount());
	}

	public PsiString psiCaptureGroup(final PsiObject oKey)
		throws PsiException
	{
		try
		{
			final String group;
			if(oKey instanceof PsiInteger)
				group=matcher.group(((PsiInteger)oKey).intValue());
			else if(oKey instanceof PsiStringy)
				group=matcher.group(((PsiStringy)oKey).stringValue());
			else
				throw new PsiTypeCheckException();
			return group!=null? new PsiString(group): null;
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
		catch(IllegalArgumentException e)
		{
			throw new PsiUndefinedException();
		}
		catch(IllegalStateException e)
		{
			throw new PsiInvalidStateException();
		}
	}

	public PsiInteger psiCaptureGroupStart(final PsiObject oKey)
		throws PsiException
	{
		try
		{
			final int start;
			if(oKey instanceof PsiInteger)
				start=matcher.start(((PsiInteger)oKey).intValue());
			else if(oKey instanceof PsiStringy)
				start=matcher.start(((PsiStringy)oKey).stringValue());
			else
				throw new PsiTypeCheckException();
			return start>=0? PsiInteger.valueOf(start): null;
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
		catch(IllegalArgumentException e)
		{
			throw new PsiUndefinedException();
		}
		catch(IllegalStateException e)
		{
			throw new PsiInvalidStateException();
		}
	}

	public PsiInteger psiCaptureGroupEnd(final PsiObject oKey)
		throws PsiException
	{
		try
		{
			final int end;
			if(oKey instanceof PsiInteger)
				end=matcher.start(((PsiInteger)oKey).intValue());
			else if(oKey instanceof PsiStringy)
				end=matcher.start(((PsiStringy)oKey).stringValue());
			else
				throw new PsiTypeCheckException();
			return end>=0? PsiInteger.valueOf(end): null;
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
		catch(IllegalArgumentException e)
		{
			throw new PsiUndefinedException();
		}
		catch(IllegalStateException e)
		{
			throw new PsiInvalidStateException();
		}
	}

	@Override
	public String typeName()
	{
		return "matcher";
	}

	private final java.util.regex.Matcher matcher;
}
