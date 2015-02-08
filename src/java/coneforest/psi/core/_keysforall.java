package coneforest.psi.core;
import coneforest.psi.*;

public class _keysforall extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		// TODO: PsiDictionarylike?
		final PsiDictionary dict=(PsiDictionary)ops[0];
		final PsiObject obj=ops[1];
		int looplevel=interpreter.pushLoopLevel();
		for(java.util.Map.Entry<String, PsiObject> entry: dict)
		{
			if(interpreter.getExitFlag())
				break;
			opstack.push(new PsiName(entry.getKey()));
			obj.invoke(interpreter);
			interpreter.handleExecutionStack(looplevel);
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
	}
}
