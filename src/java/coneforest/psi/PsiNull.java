package coneforest.psi;

/**
 *	A representation of Ψ-{@code null} object.
 */
public final class PsiNull
	implements PsiAtomic
{
	private PsiNull()
	{
	}

	/**
	 *	@return a string {@code "null"}.
	 */
	@Override
	public String getTypeName() { return "null"; }

	/**
	 *	@return a string {@code "null"}.
	 */
	@Override
	public String toSyntaxString()
	{
		return "null";
	}

	/**
	 *	Returns a Ψ-{@code boolean} indicating whether some other Ψ-object is
	 *	“equal to” this one. Return value is {@code true} if and only if other
	 *	object has {@code name} type.
	 *
	 *	@return a result.
	 */
	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(obj==NULL);
	}

	/**
	 *	A single Ψ-{@code null} object.
	 */
	public static final PsiNull NULL=new PsiNull();
}
