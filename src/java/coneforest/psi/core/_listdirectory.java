package coneforest.psi.core;
import coneforest.psi.*;

public class _listdirectory extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(Utility.psiListDirectory((PsiStringlike)opstack.popOperands(1)[0]));
		/*String dirName=Utility.fileNameToNative(((PsiStringlike)opstack.popOperands(1)[0]).getString());
		try
		{
			String[] list=new java.io.File(dirName).list();
			PsiArray array=new PsiArray();
			if(list==null)
				throw new PsiException("ioerror");
			for(String item: list)
				array.psiAppend(new PsiString(item));
			opstack.push(array);
		}
		catch(SecurityException e)
		{
			throw new PsiException("securityerror");
		}
		*/
	}
}
