package coneforest.psi;

public class PsiStringReader extends PsiReader
{
	public PsiStringReader(String stringValue)
	{
		super(new java.io.StringReader(stringValue));
	}
	
	public PsiStringReader(PsiString string)
	{
		this(string.getString());
	}
}
