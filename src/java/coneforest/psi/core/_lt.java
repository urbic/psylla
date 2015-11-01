package coneforest.psi.core;
import coneforest.psi.*;

public final class _lt extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws Throwable
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		//opstack.push(((PsiScalar)ops[0]).psiLt((PsiScalar)ops[1]));
		opstack.push((PsiObject)handle.invoke(ops[0], ops[1]));
	}

	private static final java.lang.invoke.MethodHandle handle
		=getVirtualHandle(PsiScalar.class, "psiLt", PsiBoolean.class, PsiScalar.class);
}
