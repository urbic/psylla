package coneforest.psi.core;
import coneforest.psi.*;

public class _execstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final ExecutionStack execstack=interpreter.getExecutionStack();
		PsiArray result=new PsiArray();
		for(PsiObject obj: execstack)
			result.psiAppend(obj);
		opstack.push(result);
	}
}
