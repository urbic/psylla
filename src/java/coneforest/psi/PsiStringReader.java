package coneforest.psi;

public class PsiStringReader extends PsiReader
{
	public PsiStringReader(PsiString string)
	{
		super(new java.io.StringReader(string.getString()));
	}
}
