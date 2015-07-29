package coneforest.psi;

public class PsiMatcher
	implements PsiObject
{
	public PsiMatcher(PsiStringlike stringlike, PsiRegExp regexp)
	{
		matcher=regexp.getPattern().matcher(stringlike.getString());
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
			String group=null;
			if(key instanceof PsiInteger)
				group=matcher.group(((PsiInteger)key).intValue());
			else if(key instanceof PsiStringlike)
				group=matcher.group(((PsiStringlike)key).getString());
			else
				throw new ClassCastException();
			return group!=null? new PsiString(group): PsiNull.NULL;
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
		catch(IllegalArgumentException e)
		{
			throw new PsiException("undefined");
		}
		catch(IllegalStateException e)
		{
			throw new PsiException("invalidstate");
		}
	}

	public PsiInteger psiCaptureGroupStart(final PsiInteger integer)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(matcher.start(integer.intValue()));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	public PsiInteger psiCaptureGroupEnd(final PsiInteger integer)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(matcher.end(integer.intValue()));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public String getTypeName()
	{
		return "matcher";
	}

	private java.util.regex.Matcher matcher;
}
