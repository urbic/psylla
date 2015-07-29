package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">mark</code> object.
 */
public final class PsiMark
	implements PsiAtomic
{
	private PsiMark()
	{
	}

	/**
	 *	@return a string <code class="constant">"mark"</code>.
	 */
	public String getTypeName()
	{
		return "mark";
	}

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(obj==MARK);
	}

	public static final PsiMark MARK=new PsiMark();
}
