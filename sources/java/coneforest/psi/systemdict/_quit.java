package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _quit extends PSIOperator
{
	public String getName()	{ return "quit"; }

	public void execute(PSIInterpreter interpreter)
	{
		System.exit(0);
	}
}
