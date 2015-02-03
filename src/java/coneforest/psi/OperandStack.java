package coneforest.psi;

/**
 *	An interpreter’s operand stack.
 */
public class OperandStack extends Stack<PsiObject>
{
	/*
	@Override
	public void push(PsiObject obj)
		throws PsiException
	{
		try
		{
			super.push(obj);
		}
		catch(OutOfMemoryError e)
		{
			throw new PsiException("stackoverflow");
		}
	}
	*/
}
