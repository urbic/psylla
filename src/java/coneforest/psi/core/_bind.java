package coneforest.psi.core;
import coneforest.psi.*;

public final class _bind extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.ensureSize(1);
		bind((PsiProc)opstack.peek(), interpreter.getDictStack());
	}

	private static void bind(PsiProc proc, DictStack dictstack)
	{
		bindHelper(proc, new java.util.HashSet<PsiProc>(), dictstack);
	}

	private static void bindHelper(PsiProc proc, java.util.HashSet<PsiProc> bound, DictStack dictstack)
	{
			for(int i=0; i<proc.length(); i++)
			{
				try
				{
					PsiObject obj=proc.get(i);
					if(obj instanceof PsiProc && bound.add((PsiProc)obj))
						bindHelper((PsiProc)obj, bound, dictstack);
					else if(obj instanceof PsiCommand)
					{
						PsiObject value=dictstack.load((PsiName)obj);
						if(value instanceof PsiOperator)
							proc.put(i, value);
					}
				}
				catch(PsiException e)
				{
					// NOP
				}
			}
	}
}
