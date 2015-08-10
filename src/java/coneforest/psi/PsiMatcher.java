package coneforest.psi;

public class PsiMatcher
	implements PsiObject
{
	public PsiMatcher(PsiStringy stringy, PsiRegExp regexp)
	{
		matcher=regexp.getPattern().matcher(stringy.getString());
	}

	public PsiBoolean psiFind()
	{
		return PsiBoolean.valueOf(matcher.find());
	}

	public PsiInteger psiCaptureGroupCount()
	{
		return PsiInteger.valueOf(matcher.groupCount());
	}

	public PsiObject psiCaptureGroup(final PsiObject key)
		throws PsiException
	{
		try
		{
			String group;
			if(key instanceof PsiInteger)
				group=matcher.group(((PsiInteger)key).intValue());
			else if(key instanceof PsiStringy)
				group=matcher.group(((PsiStringy)key).getString());
			else
				throw new PsiTypeCheckException();
			return group!=null? new PsiString(group): PsiNull.NULL;
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

	public PsiObject psiCaptureGroupStart(final PsiObject key)
		throws PsiException
	{
		try
		{
			int start;
			if(key instanceof PsiInteger)
				start=matcher.start(((PsiInteger)key).intValue());
			else if(key instanceof PsiStringy)
				start=matcher.start(((PsiStringy)key).getString());
			else
				throw new PsiTypeCheckException();
			return start>=0? PsiInteger.valueOf(start): PsiNull.NULL;
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

	public PsiObject psiCaptureGroupEnd(final PsiObject key)
		throws PsiException
	{
		try
		{
			int end;
			if(key instanceof PsiInteger)
				end=matcher.start(((PsiInteger)key).intValue());
			else if(key instanceof PsiStringy)
				end=matcher.start(((PsiStringy)key).getString());
			else
				throw new PsiTypeCheckException();
			return end>=0? PsiInteger.valueOf(end): PsiNull.NULL;
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
