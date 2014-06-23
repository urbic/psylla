package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _idiv extends PSIOperator
{
	public String getName()	{ return "idiv"; }

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

		if(n1 instanceof PSIInteger && n2 instanceof PSIInteger)
		{
			// TODO
			if(n2.getValue()!=0.)
			{
				Double result=((Long)n1.getValue()).doubleValue()/((Long)n2.getValue()).doubleValue();
				opstack.push(new PSIInteger(result.longValue()));
			}
			else
			{
				opstack.push(n1);
				opstack.push(n2);
				interpreter.error("undefinedresult");
			}
		}
		else
		{
			opstack.push(n1);
			opstack.push(n2);
			interpreter.error("typecheck");
		}
	}
}
