package coneforest.psi.core;
import coneforest.psi.*;

public class _sort extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject proc=opstack.pop();
		PsiObject arraylike=opstack.pop();
		try
		{
			PsiObject[] a=new PsiObject[((PsiArraylike)arraylike).length()];
			int i=0;
			for(PsiObject obj: ((PsiArraylike<PsiObject>)arraylike))
				a[i++]=obj;

			java.util.Arrays.sort(a, new Comparator(interpreter, (PsiArray)proc));

			PsiArray result=new PsiArray();
			for(PsiObject obj: a)
				result.psiAppend(obj);

			opstack.push(result);

		}
		catch(ClassCastException e)
		{
			opstack.push(arraylike);
			opstack.push(proc);
			interpreter.error(e, this);
		}
	}

	private class Comparator
		implements java.util.Comparator<PsiObject>
	{
		public Comparator(Interpreter interpreter, PsiArray proc)
		{
			this.interpreter=interpreter;
			this.proc=proc;
		}

		@Override
		public int compare(PsiObject obj1, PsiObject obj2)
		{
			OperandStack opstack=interpreter.getOperandStack();
			opstack.push(obj1);
			opstack.push(obj2);
		
			int execlevel=interpreter.getExecLevel();
			proc.invoke(interpreter);
			interpreter.handleExecutionStack(execlevel);

			return ((PsiInteger)opstack.pop()).intValue();
		}

		private Interpreter interpreter;

		private PsiArray proc;
	}
}
