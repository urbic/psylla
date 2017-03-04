package coneforest.psi.core;

/**
*	A representation of Ψ-{@code mark} object.
*/
@coneforest.psi.Type("mark")
public final class PsiMark
	implements PsiAtomic
{
	private PsiMark()
	{
	}

	@Override
	public PsiBoolean psiEq(final PsiObject o)
	{
		return PsiBoolean.valueOf(o==MARK);
	}

	/**
	*	A sole Ψ-{@code mark} object.
	*/
	public static final PsiMark MARK=new PsiMark();
}
