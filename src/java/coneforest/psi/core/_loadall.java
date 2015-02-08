package coneforest.psi.core;
import coneforest.psi.*;

public class _loadall extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiIterable<PsiObject> iterable=(PsiIterable<PsiObject>)opstack.popOperands(1)[0];
		for(PsiObject obj: (PsiIterable<PsiObject>)iterable)
			opstack.push(obj);
		opstack.push(iterable);
	}
}
