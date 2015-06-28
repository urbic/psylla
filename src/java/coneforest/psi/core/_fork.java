package coneforest.psi.core;
import coneforest.psi.*;

public class _fork extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();

		opstack.ensureSize(1);
		final PsiObject obj=opstack.pop();

		final Interpreter forkedInterpreter
			=new Interpreter(interpreter.getDictStack())
				{
					@Override
					public void run()
					{
						obj.invoke(this);
						handleExecutionStack(0);
					}
				};

		for(int i=opstack.size()-1; i>=0; i--)
			if(opstack.get(i) instanceof PsiMark)
			{
				OperandStack forkedOpstack=forkedInterpreter.getOperandStack();
				for(int j=i+1; j<opstack.size(); j++)
					forkedOpstack.push(opstack.get(j));
				opstack.setSize(i);
				opstack.push(new PsiContext(forkedInterpreter));
				forkedInterpreter.start();
				return;
			}
		throw new PsiException("unmatchedmark");
	}
}
