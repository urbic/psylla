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
		bind((PsiArray)opstack.peek(), interpreter.getDictionaryStack());
	}

	private static void bind(PsiArray array, DictionaryStack dictstack)
	{
		bindHelper(array, new java.util.HashSet<PsiArray>(), dictstack);
	}

	private static void bindHelper(PsiArray array, java.util.HashSet<PsiArray> bound, DictionaryStack dictstack)
	{
			for(int i=0; i<array.length(); i++)
			{
				try
				{
					PsiObject obj=array.psiGet(i);
					if(obj instanceof PsiArray && bound.add((PsiArray)obj))
						bindHelper((PsiArray)obj, bound, dictstack);
					else if(obj instanceof PsiCommand)
					{
						PsiObject value=dictstack.load((PsiName)obj);
						if(value instanceof PsiOperator)
							array.psiPut(i, value);
					}
				}
				catch(PsiException e)
				{
					// NOP
				}
			}
	}
}
