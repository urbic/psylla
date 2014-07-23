package coneforest.psi;

public interface PsiLogical<T extends PsiLogical>
{
	public T not();
	
	public T or(final T obj);
	
	public T and(final T obj);
	
	public T xor(final T obj);
}
