package coneforest.psi;

abstract public class PsiAbstractStringlike
	extends PsiObject
	implements
		PsiStringlike
{
	@Override
	public int length()
	{
		return getString().length();
	}

	@Override
	public boolean isEmpty()
	{
		return length()==0;
	}

	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(getString().length());
	}

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiStringlike
				&& getString().equals(((PsiStringlike)obj).getString()));
	}
	
	@Override
	public PsiBoolean psiLt(final PsiStringlike string)
	{
		return new PsiBoolean(getString().compareTo(string.getString())<0);
	}

	@Override
	public PsiBoolean psiLe(final PsiStringlike string)
	{
		return new PsiBoolean(getString().compareTo(string.getString())<=0);
	}

	@Override
	public PsiBoolean psiGt(final PsiStringlike string)
	{
		return new PsiBoolean(getString().compareTo(string.getString())>0);
	}

	@Override
	public PsiBoolean psiGe(final PsiStringlike string)
	{
		return new PsiBoolean(getString().compareTo(string.getString())>=0);
	}
}
