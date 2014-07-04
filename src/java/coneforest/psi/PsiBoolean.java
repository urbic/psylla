package coneforest.psi;

public class PsiBoolean extends PsiObject
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

	static public PsiBoolean not(final PsiBoolean p)
	{
		return new PsiBoolean(!p.getValue());
	}

	static public PsiBoolean and(final PsiBoolean p, final PsiBoolean q)
	{
		return new PsiBoolean(p.getValue() && q.getValue());
	}

	static public PsiBoolean or(final PsiBoolean p, final PsiBoolean q)
	{
		return new PsiBoolean(p.getValue() || q.getValue());
	}

	static public PsiBoolean xor(final PsiBoolean p, final PsiBoolean q)
	{
		return new PsiBoolean(p.getValue() ^ q.getValue());
	}

	private final boolean value;
}
