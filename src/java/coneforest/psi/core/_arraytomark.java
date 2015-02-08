package coneforest.psi.core;
import coneforest.psi.*;

public class _arraytomark extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
		{
			if(opstack.get(i)==PsiMark.MARK)
			{
				PsiArray array=new PsiArray();
				for(int j=i+1; j<opstack.size(); j++)
					array.psiAppend(opstack.get(j));
				opstack.setSize(i);
				opstack.push(array);
				return;
			}
		}
		throw new PsiException("unmatchedmark");
	}
}
