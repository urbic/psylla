package coneforest.psi.core;
import coneforest.psi.*;

public class _cleartomark extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
			if(opstack.get(i) instanceof PsiMark)
			{
				opstack.setSize(i);
				return;
			}
		throw new PsiException("unmatchedmark");
	}
}
