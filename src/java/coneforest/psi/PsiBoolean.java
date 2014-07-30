package coneforest.psi;

public class PsiBoolean
	extends PsiObject
	implements PsiAtomic, PsiLogical<PsiBoolean>
{
	public PsiBoolean(final boolean value)
	{
		this.value=value;
	}

	public String getTypeName() { return "boolean"; }

	public Boolean getValue()
	{
		return value;
	}

	public String toString()
	{
		return value? "true": "false";
	}

	public PsiBoolean psiNot()
	{
		return new PsiBoolean(!getValue());
	}

	public PsiBoolean psiOr(final PsiBoolean obj)
	{
		return new PsiBoolean(getValue() || obj.getValue());
	}

	public PsiBoolean psiAnd(final PsiBoolean obj)
	{
		return new PsiBoolean(getValue() && obj.getValue());
	}

	public PsiBoolean psiXor(final PsiBoolean obj)
	{
		return new PsiBoolean(getValue() ^ obj.getValue());
	}

	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiBoolean
				&& value==((PsiBoolean)obj).getValue());
	}

	private final boolean value;
}
