package coneforest.psi;

public class PsiType<T extends PsiObject>
	implements PsiObject
{
	public PsiType(Class<T> clazz)
	{
		this.clazz=clazz;
	}

	public PsiBoolean psiIsInstance(PsiObject obj)
	{
		return PsiBoolean.valueOf(clazz.isInstance(obj));
	}

	@Override
	public String getTypeName()
	{
		return "type";
	}

	private Class<T> clazz;
}
