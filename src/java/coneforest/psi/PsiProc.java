package coneforest.psi;

public class PsiProc
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
			ExecutionStack execstack=interpreter.executionStack();
			for(int i=length()-1; i>=0; i--)
				execstack.push(get(i));
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