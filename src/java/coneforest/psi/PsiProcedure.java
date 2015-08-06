package coneforest.psi;

public class PsiProcedure
	extends PsiArray
{
	@Override
	public String getTypeName()
	{
		return "proc";
	}

	@Override
	public void invoke(final Interpreter interpreter)
	{
		try
		{
			//interpreter.getExecutionStack().push(this);
			///*
			ExecutionStack execstack=interpreter.getExecutionStack();
			final int execLevel=interpreter.getExecLevel();
			for(int i=length()-1; i>=0; i--)
			{
				execstack.push(get(i));
				//interpreter.showStacks();
			}
			//interpreter.handleExecutionStack(execLevel);
			//*/
		}
		catch(PsiException e)
		{
			// TODO?
		}
	}

	@Override
	public String toSyntaxString()
	{
		return "{"+toSyntaxStringHelper(this)+"}";
	}
}
