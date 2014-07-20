package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _put extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<3)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject obj3=opstack.pop();
		PsiObject obj2=opstack.pop();
		PsiObject obj1=opstack.pop();

		if(obj1 instanceof PsiArray && obj2 instanceof PsiInteger)
		{
			try
			{
				((PsiArray)obj1).put((PsiInteger)obj2, obj3);
			}
			catch(PsiException e)
			{
				interpreter.error(e.kind());
			}
		}
		else if(obj1 instanceof PsiDictionary && obj2 instanceof PsiStringlike)
		{
			((PsiDictionary)obj1).put((PsiStringlike)obj2, obj3);
		}
		else if(obj1 instanceof PsiString && obj2 instanceof PsiInteger && obj3 instanceof PsiInteger)
		{
			try
			{
				((PsiString)obj1).put((PsiInteger)obj2, (PsiInteger)obj3);
			}
			catch(PsiException e)
			{
				interpreter.error(e.kind());
			}
		}
		else if(obj1 instanceof PsiBitVector && obj2 instanceof PsiInteger && obj3 instanceof PsiBoolean)
		{
			try
			{
				((PsiBitVector)obj1).put((PsiInteger)obj2, (PsiBoolean)obj3);
			}
			catch(PsiException e)
			{
				interpreter.error(e.kind());
			}
		}
		else
			interpreter.error("typecheck");
	}
}
