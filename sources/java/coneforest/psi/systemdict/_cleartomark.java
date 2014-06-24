package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cleartomark extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
			if(opstack.elementAt(i) instanceof PSIMark)
			{
				opstack.setSize(i);
				return;
			}
		interpreter.error("unmatchedmark");
	}
}
