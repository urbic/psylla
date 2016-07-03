package coneforest.psi.core;

public class PsiStringReader
	extends PsiReader
{
	public PsiStringReader(final String string)
	{
		super(new java.io.StringReader(string));
	}

	public PsiStringReader(final PsiStringy oStringy)
	{
		this(oStringy.stringValue());
	}

	@Override
	public String typeName()
	{
		return "stringreader";
	}
}
