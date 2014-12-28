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

	@Override
	public String getTypeName()
	{
		return "stringreader";
	}

	static
	{
		TypeRegistry.put("stringreader", PsiStringReader.class);
	}
}
