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
		bind((PsiProcedure)opstack.peek(), interpreter.getDictStack());
	}

	private static void bind(PsiProcedure proc, DictStack dictstack)
	{
		bindHelper(proc, new java.util.HashSet<PsiProcedure>(), dictstack);
	}

	private static void bindHelper(PsiProcedure proc, java.util.HashSet<PsiProcedure> bound, DictStack dictstack)
	{
			for(int i=0; i<proc.length(); i++)
			{
				try
				{
					PsiObject obj=proc.get(i);
					if(obj instanceof PsiProcedure && bound.add((PsiProcedure)obj))
						bindHelper((PsiProcedure)obj, bound, dictstack);
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
