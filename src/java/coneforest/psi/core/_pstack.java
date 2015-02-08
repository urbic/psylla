package coneforest.psi.core;
import coneforest.psi.*;

public class _pstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		// TODO reverse order
		System.out.print("OPSTACK> ");
		for(PsiObject obj: opstack)
			System.out.print(obj+" ");
		System.out.println();
	}
}
