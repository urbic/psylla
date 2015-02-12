package coneforest.psi.core;
import coneforest.psi.*;

public class _sort extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		final PsiArray array=(PsiArray)ops[0];
		final PsiObject comparator=ops[1];
		
		opstack.push(array.psiSort(new java.util.Comparator<PsiObject>()
				{
					@Override
					public int compare(PsiObject obj1, PsiObject obj2)
					{
						opstack.push(obj1);
						opstack.push(obj2);
						final int execLevel=interpreter.getExecLevel();
						comparator.invoke(interpreter);
						interpreter.handleExecutionStack(execLevel);
						// TODO: ensure stack size
						return ((PsiInteger)opstack.pop()).intValue();
					}
				}));
	}
	/*
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
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
						opstack.push(obj1);
						opstack.push(obj2);
						final int execLevel=interpreter.getExecLevel();
						comparator.invoke(interpreter);
						interpreter.handleExecutionStack(execLevel);
						// TODO: ensure stack size
						return ((PsiInteger)opstack.pop()).intValue();
					}
				});

		final PsiArray result=new PsiArray();
		for(PsiObject obj: a)
			result.psiAppend(obj);

		opstack.push(result);
	}
	*/
}
