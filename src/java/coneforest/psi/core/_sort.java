package coneforest.psi.core;
import coneforest.psi.*;

public final class _sort extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		final PsiArray array=(PsiArray)ops[0];
		final PsiObject comparator=ops[1];

		ostack.push(array.psiSort(new java.util.Comparator<PsiObject>()
				{
					@Override
					public int compare(PsiObject obj1, PsiObject obj2)
					{
						ostack.push(obj1);
						ostack.push(obj2);
						final int execLevel=interpreter.getExecLevel();
						comparator.invoke(interpreter);
						interpreter.handleExecutionStack(execLevel);
						// TODO: ensure stack size
						return ((PsiInteger)ostack.pop()).intValue();
					}
				}));
	}
	/*
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		final PsiIterable iterable=(PsiIterable)ops[0];
		final PsiObject comparator=ops[1];

		final PsiObject[] a=new PsiObject[((PsiLengthy)iterable).length()];
		int i=0;

		for(PsiObject obj: ((PsiIterable<PsiObject>)iterable))
			a[i++]=obj;

		java.util.Arrays.sort(a, new java.util.Comparator<PsiObject>()
				{
					@Override
					public int compare(PsiObject obj1, PsiObject obj2)
					{
						ostack.push(obj1);
						ostack.push(obj2);
						final int execLevel=interpreter.getExecLevel();
						comparator.invoke(interpreter);
						interpreter.handleExecutionStack(execLevel);
						// TODO: ensure stack size
						return ((PsiInteger)ostack.pop()).intValue();
					}
				});

		final PsiArray result=new PsiArray();
		for(PsiObject obj: a)
			result.psiAppend(obj);

		ostack.push(result);
	}
	*/
}
