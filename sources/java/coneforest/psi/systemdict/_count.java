package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _count extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		opstack.push(new PSIInteger(opstack.size()));
	}
}
