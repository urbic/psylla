package coneforest.psi.core;
import coneforest.psi.*;

public class _exit extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		if(interpreter.currentLoopLevel()==-1)
			throw new PsiInvalidExitException();
		interpreter.getExecutionStack().setSize(interpreter.currentLoopLevel());
		interpreter.setExitFlag(true);
	}
}
