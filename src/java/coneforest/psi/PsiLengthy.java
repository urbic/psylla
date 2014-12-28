package coneforest.psi;

public interface PsiLengthy
	extends PsiObject
{
	int length();

	boolean isEmpty();

	PsiInteger psiLength();

	PsiBoolean psiIsEmpty();

	String toStringHelper(PsiLengthy lengthy);

	static class Registrator
	{
		static
		{
			System.out.println("REGISTRATOR");
			TypeRegistry.put("lengthy", PsiLengthy.class);
		}
	}
}
