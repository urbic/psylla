package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _appendall extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject iterable=opstack.pop();
		PsiObject appendable=opstack.pop();
		try
		{
			((PsiAppendable)appendable).psiAppendAll((PsiIterable)iterable);
		}
		catch(ClassCastException e)
		{
			opstack.push(appendable);
			opstack.push(iterable);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(appendable);
			opstack.push(iterable);
			interpreter.error(e.kind(), this);
		}
	}
}
