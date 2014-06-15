package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _print extends PSIOperator
{
	public String getName()	{ return "print"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()==0)
			interpreter.error("stackunderflow");
		else
			System.out.println(opstack.pop());
	}
}
