package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _countdictstack extends PSIOperator
{
	public String getName()	{ return "countdictstack"; }

	public void execute(PSIInterpreter interpreter)
	{
		interpreter.getOperandStack().push(new PSIInteger(interpreter.getDictionaryStack().size()));
	}
}
