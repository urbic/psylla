package coneforest.psi.core;

public class PsiMatcher
	implements PsiObject
{
	public PsiMatcher(PsiStringy stringy, PsiRegExp regexp)
	{
		matcher=regexp.getPattern().matcher(stringy.stringValue());
	}

	public PsiBoolean psiFind()
	{
		return PsiBoolean.valueOf(matcher.find());
	}

	public PsiInteger psiCaptureGroupCount()
	{
		return PsiInteger.valueOf(matcher.groupCount());
	}

	public PsiString psiCaptureGroup(final PsiObject key)
		throws PsiException
	{
		try
		{
			String group;
			if(key instanceof PsiInteger)
				group=matcher.group(((PsiInteger)key).intValue());
			else if(key instanceof PsiStringy)
				group=matcher.group(((PsiStringy)key).stringValue());
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

	public PsiInteger psiCaptureGroupStart(final PsiObject key)
		throws PsiException
	{
		try
		{
			int start;
			if(key instanceof PsiInteger)
				start=matcher.start(((PsiInteger)key).intValue());
			else if(key instanceof PsiStringy)
				start=matcher.start(((PsiStringy)key).stringValue());
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

	public PsiInteger psiCaptureGroupEnd(final PsiObject key)
		throws PsiException
	{
		try
		{
			int end;
			if(key instanceof PsiInteger)
				end=matcher.start(((PsiInteger)key).intValue());
			else if(key instanceof PsiStringy)
				end=matcher.start(((PsiStringy)key).stringValue());
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
	public String getTypeName()
	{
		return "matcher";
	}

	private java.util.regex.Matcher matcher;
}
