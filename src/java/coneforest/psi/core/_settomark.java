package coneforest.psi.core;
import coneforest.psi.*;

public class _settomark extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
		{
			if(opstack.get(i) instanceof PsiMark)
			{
				PsiSet set=new PsiSet();
				while(opstack.size()>i+1)
					set.psiAppend(opstack.pop());
				opstack.pop();
				opstack.push(set);
				return;
			}
		}
		interpreter.error("unmatchedmark", this);
	}
}
