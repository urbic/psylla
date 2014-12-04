package coneforest.psi.core;
import coneforest.psi.*;

public class _counttomark extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
		{
			if(opstack.get(i) instanceof PsiMark)
			{
				opstack.push(new PsiInteger(opstack.size()-1-i));
				return;
			}
		}
		interpreter.error("unmatchedmark", this);
	}
}
