package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _get extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject obj2=opstack.pop();
		PsiObject obj1=opstack.pop();

		if(obj1 instanceof PsiArray && obj2 instanceof PsiInteger)
		{
			try
			{
				opstack.push(((PsiArray)obj1).get((PsiInteger)obj2));
			}
			catch(PsiException e)
			{
				interpreter.error(e.kind());
				return;
			}
		}
		else if(obj1 instanceof PsiDictionary && obj2 instanceof PsiStringlike)
		{
			try
			{
				opstack.push(((PsiDictionary)obj1).get((PsiStringlike)obj2));
			}
			catch(PsiException e)
			{
				interpreter.error(e.kind());
				return;
			}
		}
		else if(obj1 instanceof PsiString && obj2 instanceof PsiInteger)
		{
			try
			{
				opstack.push(((PsiString)obj1).get((PsiInteger)obj2));
			}
			catch(PsiException e)
			{
				interpreter.error(e.kind());
				return;
			}
		}
		else if(obj1 instanceof PsiBitVector && obj2 instanceof PsiInteger)
		{
			try
			{
				opstack.push(((PsiBitVector)obj1).get((PsiInteger)obj2));
			}
			catch(PsiException e)
			{
				interpreter.error(e.kind());
				return;
			}
		}
		else
			interpreter.error("typecheck");
	}
}
