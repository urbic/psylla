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

	public Boolean booleanValue()
	{
		return value;
	}

	public String toString()
	{
		return value? "true": "false";
	}

	public PsiBoolean psiNot()
	{
		return new PsiBoolean(!value);
	}

	public PsiBoolean psiOr(final PsiBoolean bool)
	{
		return new PsiBoolean(value || bool.value);
	}

	public PsiBoolean psiAnd(final PsiBoolean bool)
	{
		return new PsiBoolean(value && bool.value);
	}

	public PsiBoolean psiXor(final PsiBoolean bool)
	{
		return new PsiBoolean(value ^ bool.value);
	}

	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiBoolean
				&& value==((PsiBoolean)obj).value);
	}
	
	@Override
	public int hashCode()
	{
		return value? 1: 0;
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof PsiBoolean
				&& psiEq((PsiBoolean)object).value;
	}

	private final boolean value;
}
