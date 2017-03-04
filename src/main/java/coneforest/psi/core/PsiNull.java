package coneforest.psi.core;

/**
*	A representation of Ψ-{@code null}, a type of a void placeholder. There is
*	the only instance of this class, {@link #NULL}.
*/
@coneforest.psi.Type("null")
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
	public PsiBoolean psiEq(final PsiObject o)
	{
		return PsiBoolean.valueOf(o==NULL);
	}

	/**
	*	A sole Ψ-{@code null} object.
	*/
	public static final PsiNull NULL=new PsiNull();
}
