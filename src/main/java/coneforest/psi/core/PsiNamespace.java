package coneforest.psi.core;

public class PsiNameSpace
	extends PsiDict
{
	@Override
	public String typeName()
	{
		return "namespace";
	}

	public PsiNameSpace(final String prefix)
	{
		this.prefix=prefix;
	}

	public PsiNameSpace(final PsiStringy oPrefix)
	{
		this(oPrefix.stringValue());
	}

	public String toSyntaxString()
	{
		return "-namespace:"+prefix+"-";
	}

	private final String prefix;
}
