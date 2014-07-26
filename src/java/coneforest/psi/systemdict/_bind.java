package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _bind extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}

		PsiObject array=opstack.peek();

		if(array instanceof PsiArray)
			bind((PsiArray)array, interpreter.getDictionaryStack());
		else
			interpreter.error("typecheck");
	}
	
	private static void bind(PsiArray array, DictionaryStack dictstack)
	{
		java.util.HashSet<PsiArray> bound=new java.util.HashSet<PsiArray>();
		bindHelper(array, bound, dictstack);
	}

	private static void bindHelper(PsiArray array, java.util.HashSet<PsiArray> bound, DictionaryStack dictstack)
	{
		for(int i=0; i<((PsiArray)array).length().getValue(); i++)
		{
			try
			{
				PsiObject obj=((PsiArray)array).get(i);
				if(obj instanceof PsiArray && bound.add((PsiArray)obj))
					bindHelper((PsiArray)obj, bound, dictstack);
				else if(obj instanceof PsiName && ((PsiName)obj).isExecutable())
				{
					PsiObject value=dictstack.load((PsiName)obj);
					if(value instanceof PsiOperator)
						((PsiArray)array).set(i, value);
				}
			}
			catch(PsiException e)
			{
			}
		}
	}
}
