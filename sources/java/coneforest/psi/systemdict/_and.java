package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _and extends PSIOperator
{
	public String getName()	{ return "and"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PSIObject n2=opstack.pop();
		PSIObject n1=opstack.pop();

		if(n1 instanceof PSIBoolean && n2 instanceof PSIBoolean)
			opstack.push(PSIBoolean.and((PSIBoolean)n1, (PSIBoolean)n2));
		else if(n1 instanceof PSIInteger && n2 instanceof PSIInteger)
			opstack.push(PSIInteger.and((PSIInteger)n1, (PSIInteger)n2));
		else
		{
			opstack.push(n1);
			opstack.push(n2);
			interpreter.error("typecheck");
		}
	}
}
