package coneforest.psi;

public class PsiStringReader extends PsiReader
{
	public PsiStringReader(PsiString oString)
	{
		super(new java.io.StringReader(oString.getValue()));
	}
}
