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
			ExecutionStack execstack=interpreter.getExecutionStack();
			for(int i=length()-1; i>=0; i--)
				execstack.push(get(i));
		}
		catch(PsiException e)
		{
			// TODO?
		}
	}

	@Override
	public String toString()
	{
		return "{"+toStringHelper(this)+"}";
	}
}
