package coneforest.psi.core;
import coneforest.psi.*;

public final class _mul extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws Throwable
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		//opstack.push(((PsiArithmetic)ops[0]).psiMul((PsiArithmetic)ops[1]));
		opstack.push((PsiObject)handle.invoke(ops[0], ops[1]));
	}

	private static final java.lang.invoke.MethodHandle handle
		=getVirtualHandle(PsiArithmetic.class, "psiMul", PsiArithmetic.class, PsiArithmetic.class);
}
