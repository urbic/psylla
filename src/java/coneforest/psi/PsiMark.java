package coneforest.psi;

public final class PsiMark extends PsiObject
{
	public String getTypeName() { return "mark"; }

	public String toString()
	{
		return "-mark-";
	}

	public PsiBoolean eq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiMark);
	}
}
