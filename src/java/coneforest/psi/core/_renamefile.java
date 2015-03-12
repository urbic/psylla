package coneforest.psi.core;
import coneforest.psi.*;

public class _renamefile extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		PsiObject[] ops=opstack.popOperands(2);
		PsiStringlike oldName=(PsiStringlike)ops[0];
		PsiStringlike newName=(PsiStringlike)ops[1];
		Utility.psiRenameFile(oldName, newName);
	}
}