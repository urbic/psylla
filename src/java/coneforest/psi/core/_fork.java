package coneforest.psi.core;
import coneforest.psi.*;

public final class _fork extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack ostack=interpreter.operandStack();

		ostack.ensureSize(2);
		final PsiObject obj=ostack.pop();

		final Interpreter forkedInterpreter
			=new Interpreter(interpreter.dictStack())
				{
					@Override
					public void run()
					{
						obj.invoke(this);
						handleExecutionStack(0);
					}
				};

		for(int i=ostack.size()-1; i>=0; i--)
			if(ostack.get(i)==PsiMark.MARK)
			{
				OperandStack forkedOpstack=forkedInterpreter.operandStack();
				for(int j=i+1; j<ostack.size(); j++)
					forkedOpstack.push(ostack.get(j));
				ostack.setSize(i);
				ostack.push(forkedInterpreter);
				forkedInterpreter.start();
				return;
			}
		throw new PsiUnmatchedMarkException();
	}
}
