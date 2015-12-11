package coneforest.psi.core;
import coneforest.psi.*;

public final class _arraytomark extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		for(int i=ostack.size()-1; i>=0; i--)
		{
			if(ostack.get(i)==PsiMark.MARK)
			{
				PsiArray array=new PsiArray();
				for(int j=i+1; j<ostack.size(); j++)
					array.psiAppend(ostack.get(j));
				ostack.setSize(i);
				ostack.push(array);
				return;
			}
		}
		throw new PsiUnmatchedMarkException();
	}
}
