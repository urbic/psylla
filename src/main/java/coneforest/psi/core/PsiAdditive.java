package coneforest.psi.core;

/**
*	A representation of Ψ-{@code additive}, a type of object that is an operand
*	of arithmetic operation. This interface declares methods for addition,
*	subtraction and negation.
*
*	@param <T> a type of the second operand at binary operation.
*/
@coneforest.psi.Type("additive")
public interface PsiAdditive<T extends PsiAdditive>
	extends PsiObject
{

	/**
	*	Returns a Ψ-{@code additive} representing the result of arithmetic
	*	negation of this object.
	*
	*	@return a negation.
	*/
	public T psiNeg();

	/**
	*	Returns a Ψ-{@code additive} representing the result of arithmetic
	*	addition of given object to this object.
	*
	*	@param oAdditive given object.
	*	@return a sum.
	*/
	public T psiAdd(final T oAdditive);
	//public <U extends T> T psiAdd(final U oAdditive);

	/**
	*	Returns a Ψ-{@code additive} representing the result of arithmetic
	*	subtraction of given object from this object.
	*
	*	@param oAdditive given object.
	*	@return a difference.
	*/
	public T psiSub(final T oAdditive);

	//public static final PsiType<PsiAdditive> TYPE
	//	=new PsiType<PsiAdditive>("additive");
}
