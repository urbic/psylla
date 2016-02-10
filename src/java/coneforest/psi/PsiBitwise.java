package coneforest.psi;

public interface PsiBitwise
	extends PsiObject
{
	@Override
	default public String getTypeName()
	{
		return "bitwise";
	}

	public PsiBitwise psiClearBit(final PsiInteger oBit)
		throws PsiException;

	public PsiBitwise psiFlipBit(final PsiInteger oBit)
		throws PsiException;

	public PsiBitwise psiSetBit(final PsiInteger oBit)
		throws PsiException;

	public PsiBoolean psiTestBit(final PsiInteger oBit)
		throws PsiException;

	public PsiInteger psiBitShift(final PsiInteger oShift);
}
