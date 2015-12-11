package coneforest.psi.core;
import coneforest.psi.*;

public final class _where extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiDictlike dict=interpreter.dictStack().where((PsiStringy)ostack.popOperands(1)[0]);
		if(dict!=null)
			ostack.push(dict);
		ostack.push(PsiBoolean.valueOf(dict!=null));
	}
}
