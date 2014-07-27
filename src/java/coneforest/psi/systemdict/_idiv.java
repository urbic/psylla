package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _idiv extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject n2=opstack.pop();
		PsiObject n1=opstack.pop();

		if(n1 instanceof PsiInteger && n2 instanceof PsiInteger)
		{
			// TODO
			if(((PsiInteger)n2).getValue()!=0.)
			{
				Double result=(((PsiInteger)n1).getValue()).doubleValue()/(((PsiInteger)n2).getValue()).doubleValue();
				opstack.push(new PsiInteger(result.longValue()));
			}
			else
			{
				opstack.push(n1);
				opstack.push(n2);
				interpreter.error("undefinedresult", this);
			}
		}
		else
		{
			opstack.push(n1);
			opstack.push(n2);
			interpreter.error("typecheck", this);
		}
	}
}
