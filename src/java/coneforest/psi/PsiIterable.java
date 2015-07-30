package coneforest.psi;

public interface PsiIterable<T>
	extends
		PsiObject,
		Iterable<T>
{
	@Override
	default public String getTypeName()
	{
		return "iterable";
	}

	public java.util.Iterator<T> iterator();
}
