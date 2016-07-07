package coneforest.psi.core;

/**
*	A representation of Ψ-{@code bitwise}, a type of object that is an operand
*	of bitwise operation. This interface declares methods for setting,
*	clearing, testing of certain bits and bitwise shift.
*/
public interface PsiBitwise<T extends PsiBitwise>
	extends PsiLogical<T>
{
	/**
	*	@return a string {@code "bitwise"}.
	*/
	@Override
	default public String typeName()
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

	public PsiBitwise psiBitShift(final PsiInteger oShift);
}
