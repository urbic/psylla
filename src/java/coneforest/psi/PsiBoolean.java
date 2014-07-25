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

	public PsiBoolean not()
	{
		return new PsiBoolean(!getValue());
	}

	public PsiBoolean or(final PsiBoolean obj)
	{
		return new PsiBoolean(getValue() || obj.getValue());
	}

	public PsiBoolean and(final PsiBoolean obj)
	{
		return new PsiBoolean(getValue() && obj.getValue());
	}

	public PsiBoolean xor(final PsiBoolean obj)
	{
		return new PsiBoolean(getValue() ^ obj.getValue());
	}


	/*public static PsiBoolean not(final PsiBoolean p)
	{
		return new PsiBoolean(!p.getValue());
	}

	public static PsiBoolean and(final PsiBoolean p, final PsiBoolean q)
	{
		return new PsiBoolean(p.getValue() && q.getValue());
	}

	public static PsiBoolean or(final PsiBoolean p, final PsiBoolean q)
	{
		return new PsiBoolean(p.getValue() || q.getValue());
	}

	public static PsiBoolean xor(final PsiBoolean p, final PsiBoolean q)
	{
		return new PsiBoolean(p.getValue() ^ q.getValue());
	}*/

	public PsiBoolean eq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiBoolean
				&& value==((PsiBoolean)obj).getValue());
	}

	private final boolean value;
}
