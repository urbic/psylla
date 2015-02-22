package coneforest.psi.core;
import coneforest.psi.*;

public class _fork extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		
		final Interpreter forkedInterpreter=interpreter.forkedInterpreter();

		opstack.ensureSize(1);
		final PsiObject obj=opstack.pop();
		final PsiContext context=new PsiContext(forkedInterpreter,
			new Thread()
			{
				@Override
				public void run()
				{
					obj.invoke(forkedInterpreter);
					forkedInterpreter.handleExecutionStack(0);
				}
			});

		for(int i=opstack.size()-1; i>=0; i--)
			if(opstack.get(i) instanceof PsiMark)
			{
				OperandStack forkedOpstack=forkedInterpreter.getOperandStack();
				for(int j=i+1; j<opstack.size(); j++)
					forkedOpstack.push(opstack.get(j));
				opstack.setSize(i);
				opstack.push(context);
				context.start();
				return;
			}
		throw new PsiException("unmatchedmark");
	}
}
