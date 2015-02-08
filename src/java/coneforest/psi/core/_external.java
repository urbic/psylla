package coneforest.psi.core;
import coneforest.psi.*;

public class _external extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(((PsiClassLoader)interpreter.getSystemDictionary().psiGet("classpath"))
				.psiExternal(((PsiStringlike)opstack.popOperands(1)[0]).getString()));
	}
}
