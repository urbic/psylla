package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">null</code> object.
 */
public final class PsiNull
	implements PsiAtomic
{
	private PsiNull()
	{
	}

	/**
	 *	@return a string <code class="constant">"null"</code>.
	 */
	@Override
	public String getTypeName() { return "null"; }

	/**
	 *	@return a string <code class="constant">"null"</code>.
	 */
	@Override
	public String toSyntaxString()
	{
		return "null";
	}

	/**
	 *	Returns a boolean Ψ-object indicating whether some other Ψ-object is
	 *	“equal to” this one. Return value is <code class="constant">true</code>
	 *	if and only if other object has <code class="type">name</code> type.
	 *
	 *	@return a result.
	 */
	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(obj==NULL);
	}

	/**
	 *	A single Ψ-<code class="type">null</code> object.
	 */
	public static final PsiNull NULL=new PsiNull();
}
