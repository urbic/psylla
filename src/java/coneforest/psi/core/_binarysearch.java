package coneforest.psi.core;
import coneforest.psi.*;

public final class _binarysearch extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(3);
		final PsiArray array=(PsiArray)ops[0];
		final PsiObject key=ops[1];
		final PsiProc comparator=(PsiProc)ops[2];

		final PsiInteger index=array.psiBinarySearch(key, comparator);
		final int indexValue=index.intValue();
		if(indexValue>=0)
		{
			opstack.push(index);
			opstack.push(PsiBoolean.TRUE);
		}
		else
		{
			opstack.push(PsiInteger.valueOf(-indexValue-1));
			opstack.push(PsiBoolean.FALSE);
		}
	}
}
