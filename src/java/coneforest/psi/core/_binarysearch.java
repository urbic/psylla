package coneforest.psi.core;
import coneforest.psi.*;

public final class _binarysearch extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(3);
		final PsiArray array=(PsiArray)ops[0];
		final PsiObject key=ops[1];
		final PsiProc comparator=(PsiProc)ops[2];

		final PsiInteger index=array.psiBinarySearch(key, comparator);
		final int indexValue=index.intValue();
		if(indexValue>=0)
		{
			ostack.push(index);
			ostack.push(PsiBoolean.TRUE);
		}
		else
		{
			ostack.push(PsiInteger.valueOf(-indexValue-1));
			ostack.push(PsiBoolean.FALSE);
		}
	}
}
