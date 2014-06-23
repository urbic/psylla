package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _pstack extends PSIOperator
{
	public String getName()	{ return "pstack"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		for(PSIObject obj: opstack)
			System.out.print(obj+" ");
	}
}
