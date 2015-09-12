package coneforest.psi.core;
import coneforest.psi.*;

public final class _where extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		PsiDictlike dict=interpreter.getDictStack().where((PsiStringy)opstack.popOperands(1)[0]);
		if(dict!=null)
			opstack.push(dict);
		opstack.push(PsiBoolean.valueOf(dict!=null));
	}
}
