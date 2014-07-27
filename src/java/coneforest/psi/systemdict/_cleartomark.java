package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cleartomark extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
			if(opstack.get(i) instanceof PsiMark)
			{
				opstack.setSize(i);
				return;
			}
		interpreter.error("unmatchedmark", this);
	}
}
