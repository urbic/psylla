package coneforest.psi.core;
import coneforest.psi.*;

public class _bind extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			throw new PsiException("stackunderflow");
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
