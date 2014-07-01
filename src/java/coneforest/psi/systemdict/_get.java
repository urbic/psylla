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
			long obj2Value=((PsiInteger)obj2).getValue();
			if(obj2Value<((PsiArray)obj1).size() && obj2Value>=0)
				opstack.push(((PsiArray)obj1).get((int)obj2Value));
			else
				interpreter.error("rangecheck");
		}
		else if(obj1 instanceof PsiDictionary && obj2 instanceof PsiStringlike)
		{
			if(((PsiDictionary)obj2).containsKey((PsiStringlike)obj2))
				opstack.push(((PsiDictionary)obj1).get((PsiStringlike)obj2));
			else
				interpreter.error("undefined");
		}
		// TODO string type
		else
			interpreter.error("typecheck");
	}
}
