package coneforest.psi.core;

public class PsiStringReader
	extends PsiReader
{
	public PsiStringReader(String stringValue)
	{
		super(new java.io.StringReader(stringValue));
	}

	public PsiStringReader(PsiStringy stringy)
	{
		this(stringy.stringValue());
	}

	@Override
	public String getTypeName()
	{
		return "stringreader";
	}
}
