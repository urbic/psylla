package coneforest.psi;

public class PsiStringReader extends PsiReader
{
	public PsiStringReader(String stringValue)
	{
		super(new java.io.StringReader(stringValue));
	}
	
	public PsiStringReader(PsiStringlike stringlike)
	{
		this(stringlike.getString());
	}
}
