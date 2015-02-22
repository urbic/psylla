package coneforest.psi.core;
import coneforest.psi.*;

public class _quit extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		//System.exit(0);
		//System.out.println(Thread.currentThread());
		//Thread.currentThread().interrupt();
		//Thread.currentThread().stop();
		interpreter.quit();
	}
}
