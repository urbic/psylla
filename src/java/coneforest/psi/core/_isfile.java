package coneforest.psi.core;
import coneforest.psi.*;

public class _isfile extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject stringlike=opstack.pop();
		try
		{
			String name=Utility.fileNameToNative(((PsiStringlike)stringlike).getString());
			try
			{
				opstack.push(new PsiBoolean((new java.io.File(name)).isFile()));
			}
			catch(SecurityException e)
			{
				throw new PsiException("security");
			}
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(stringlike);
			interpreter.error(e, this);
		}
	}
}