package coneforest.psi;

/**
 *	A representation of Ψ <code class="type">set</code> object.
 */
public class PsiSet
	extends PsiAbstractSet<PsiObject>
{
	public PsiSet()
	{
		this(new java.util.HashSet<PsiObject>());
	}

	public PsiSet(java.util.HashSet<PsiObject> setValue)
	{
		set=setValue;
	}

	public PsiSet(PsiSet setValue)
	{
		this((java.util.HashSet<PsiObject>)setValue.set.clone());
	}

	@Override
	public PsiSet psiClone()
	{
		return new PsiSet(this);
	}

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
		return PsiBoolean.valueOf(set.contains(obj));
	}

	@Override
	public void psiClear()
	{
		set.clear();
	}

	@Override
	public java.util.Iterator<PsiObject> iterator()
	{
		return set.iterator();
	}

	private java.util.HashSet<PsiObject> set=new java.util.HashSet<PsiObject>();
}
