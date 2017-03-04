package coneforest.psi.core;

@coneforest.psi.Type("stringreader")
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
}
