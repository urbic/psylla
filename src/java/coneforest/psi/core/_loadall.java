package coneforest.psi.core;
import coneforest.psi.*;

public class _loadall extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
		}

		final PsiObject iterable=opstack.pop();
		try
		{
			for(PsiObject obj: (PsiIterable<PsiObject>)iterable)
				opstack.push(obj);
			opstack.push(iterable);
		}
		catch(ClassCastException e)
		{
			// TODO
			interpreter.error(e, this);
		}
	}
}
