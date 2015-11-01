package coneforest.psi.core;
import coneforest.psi.*;

public final class _mod extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		//throws ClassCastException, PsiException
		throws Throwable
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		//opstack.push(((PsiInteger)ops[0]).psiMod((PsiInteger)ops[1]));
		//opstack.push((PsiObject)handle.invoke(ops[0], ops[1]));
		opstack.push((PsiObject)handle.invoke(ops[0], ops[1]));
	}

	private static final java.lang.invoke.MethodHandle handle
		=getVirtualHandle(PsiInteger.class, "psiMod", PsiInteger.class, PsiInteger.class);
}
