package coneforest.psi.core;
import coneforest.psi.*;

public class _lsdir extends PsiOperator
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
			String dirName=((PsiStringlike)stringlike).getString()
					.replace('/', java.io.File.separatorChar);
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
			interpreter.error(e, this);
		}
	}
}
