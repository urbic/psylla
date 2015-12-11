package coneforest.psi.core;
import coneforest.psi.*;

public final class _dicttomark extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		for(int i=ostack.size()-1; i>=0; i--)
		{
			if(ostack.get(i)==PsiMark.MARK)
			{
				if((ostack.size()-i) % 2==0)
					throw new PsiRangeCheckException();
				PsiDict dict=new PsiDict();

				for(int j=i+1; j<ostack.size(); j++)
				{
					PsiStringy key=(PsiStringy)ostack.get(j++);
					PsiObject obj=ostack.get(j);
					dict.psiPut(key, obj);
				}
				ostack.setSize(i);
				ostack.push(dict);
				return;
			}
		}
		throw new PsiUnmatchedMarkException();
	}
}
