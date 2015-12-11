package coneforest.psi.core;
import coneforest.psi.*;

public final class _settomark extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		for(int i=ostack.size()-1; i>=0; i--)
			if(ostack.get(i)==PsiMark.MARK)
			{
				PsiSet set=new PsiSet();
				while(ostack.size()>i+1)
					set.psiAppend(ostack.pop());
				ostack.pop();
				ostack.push(set);
				return;
			}
		throw new PsiUnmatchedMarkException();
	}
}
