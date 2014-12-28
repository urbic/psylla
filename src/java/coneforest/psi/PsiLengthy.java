package coneforest.psi;

public interface PsiLengthy
{
	public int length();

	public boolean isEmpty();

	public PsiInteger psiLength();

	public PsiBoolean psiIsEmpty();

	public String toString(PsiLengthy lengthy);

	static class Registrator
	{
		static
		{
			System.out.println("REGISTRATOR");
			TypeRegistry.put("lengthy", PsiLengthy.class);
		}
	}
}
