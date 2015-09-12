package coneforest.psi.core;
import coneforest.psi.*;

public final class _settomark extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
			if(opstack.get(i)==PsiMark.MARK)
			{
				PsiSet set=new PsiSet();
				while(opstack.size()>i+1)
					set.psiAppend(opstack.pop());
				opstack.pop();
				opstack.push(set);
				return;
			}
		throw new PsiUnmatchedMarkException();
	}
}
