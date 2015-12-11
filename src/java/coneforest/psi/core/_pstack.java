package coneforest.psi.core;
import coneforest.psi.*;

public final class _pstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		final OperandStack ostack=interpreter.operandStack();
		// TODO reverse order
		System.out.print("OPSTACK> ");
		for(PsiObject obj: ostack)
			System.out.print(obj.toSyntaxString()+" ");
		System.out.println();
	}
}
