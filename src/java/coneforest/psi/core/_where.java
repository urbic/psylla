package coneforest.psi.core;
import coneforest.psi.*;

public class _where extends PsiOperator
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

		final PsiObject key=opstack.pop();
		try
		{
			PsiDictionarylike dict=interpreter.getDictionaryStack().where((PsiStringlike)key);
			if(dict!=null)
				opstack.push(dict);
			opstack.push(PsiBoolean.valueOf(dict!=null));
		}
		catch(ClassCastException e)
		{
			opstack.push(key);
			interpreter.handleError(e, this);
		}
	}
}
