package coneforest.psi;

public class PsiSet
	extends PsiAbstractSet<PsiObject>
{
	@Override
	public int length()
	{
		return set.size();
	}

	@Override
	public void psiAppend(PsiObject obj)
	{
		set.add(obj);
	}

	@Override
	public void psiRemove(PsiObject obj)
	{
		set.remove(obj);
	}

	@Override
	public PsiBoolean psiContains(PsiObject obj)
	{
		return new PsiBoolean(set.contains(obj));
	}

	@Override
	public java.util.Iterator<PsiObject> iterator()
	{
		return set.iterator();
	}

	private java.util.HashSet<PsiObject> set=new java.util.HashSet<>();
}
