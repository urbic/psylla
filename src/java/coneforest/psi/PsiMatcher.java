package coneforest.psi;

public class PsiMatcher
	extends PsiAbstractObject
{
	public PsiMatcher(PsiStringlike stringlike, PsiRegExp regexp)
	{
		matcher=regexp.getPattern().matcher(stringlike.getString());
	}

	public PsiBoolean psiFind()
	{
		return new PsiBoolean(matcher.find());
	}

	public PsiString psiGetGroup(PsiInteger number)
		throws PsiException
	{
		try
		{
			return new PsiString(matcher.group(number.intValue()));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	public PsiInteger psiGetGroupStart(PsiInteger number)
		throws PsiException
	{
		try
		{
			return new PsiInteger(matcher.start(number.intValue()));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	public PsiInteger psiGetGroupEnd(PsiInteger number)
		throws PsiException
	{
		try
		{
			return new PsiInteger(matcher.end(number.intValue()));
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
