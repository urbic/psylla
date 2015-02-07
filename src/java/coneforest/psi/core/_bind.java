package coneforest.psi.core;
import coneforest.psi.*;

public class _bind extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject array=opstack.peek();
		try
		{
			bind((PsiArray)array, interpreter.getDictionaryStack());
		}
		catch(ClassCastException e)
		{
			opstack.push(array);
			interpreter.handleError(e, this);
		}
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
