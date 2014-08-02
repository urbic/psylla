package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _where extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}

		PsiObject key=opstack.pop();
		try
		{
			PsiDictionary dict=interpreter.getDictionaryStack().where((PsiStringlike)key);
			if(dict!=null)
				opstack.push(dict);
			opstack.push(new PsiBoolean(dict!=null));
		}
		catch(ClassCastException e)
		{
			opstack.push(key);
			interpreter.error("typecheck");
		}
	}
}
