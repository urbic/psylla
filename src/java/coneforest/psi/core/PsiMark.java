package coneforest.psi.core;

/**
*	A representation of Ψ-{@code mark} object.
*/
public final class PsiMark
	implements PsiAtomic
{
	private PsiMark()
	{
	}

	/**
	*	@return a string {@code "mark"}.
	*/
	public String typeName()
	{
		return "mark";
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
