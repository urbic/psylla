package coneforest.psi.core;
import coneforest.psi.*;

public class _dicttomark extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
		{
			if(opstack.get(i)==PsiMark.MARK)
			{
				if((opstack.size()-i) % 2==0)
					throw new PsiException("rangecheck");
				PsiDict dict=new PsiDict();

				for(int j=i+1; j<opstack.size(); j++)
				{
					PsiStringy key=(PsiStringy)opstack.get(j++);
					PsiObject obj=opstack.get(j);
					dict.psiPut(key, obj);
				}
				opstack.setSize(i);
				opstack.push(dict);
				return;
			}
		}
		throw new PsiException("unmatchedmark");
	}
}
