package coneforest.psi.core;
import coneforest.psi.*;

public class _sort extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject proc=opstack.pop();
		final PsiObject iterable=opstack.pop();
		try
		{
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
							proc.invoke(interpreter);
							interpreter.handleExecutionStack(execLevel);

							return ((PsiInteger)opstack.pop()).intValue();
						}
					});

			final PsiArray result=new PsiArray();
			for(PsiObject obj: a)
				result.psiAppend(obj);

			opstack.push(result);

		}
		catch(ClassCastException e)
		{
			opstack.push(iterable);
			opstack.push(proc);
			interpreter.handleError(e, this);
		}
	}
}
