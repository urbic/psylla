package coneforest.psi.core;
import coneforest.psi.*;

public final class _cleartomark extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		for(int i=ostack.size()-1; i>=0; i--)
			if(ostack.get(i)==PsiMark.MARK)
			{
				ostack.setSize(i);
				return;
			}
		throw new PsiUnmatchedMarkException();
	}
}
