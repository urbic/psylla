package coneforest.psi.core;
import coneforest.psi.*;

public class _lsdir extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject stringlike=opstack.pop();
		try
		{
			String dirName=Utility.fileNameToNative(((PsiStringlike)stringlike).getString());
			try
			{
				String[] list=new java.io.File(dirName).list();
				PsiArray array=new PsiArray();
				if(array==null)
					throw new PsiException("ioerror");
				for(String item: list)
					array.psiAppend(new PsiString(item));
				opstack.push(array);
			}
			catch(SecurityException e)
			{
				throw new PsiException("security");
			}
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(stringlike);
			interpreter.handleError(e, this);
		}
	}
}
