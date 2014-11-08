package coneforest.psi.core;
import coneforest.psi.*;

public class _dicttomark extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		try
		{
			for(int i=opstack.size()-1; i>=0; i--)
			{
				if(opstack.get(i) instanceof PsiMark)
				{
					if(opstack.size()-1-i % 2==1)
						throw new PsiException("rangecheck");
					PsiDictionary dict=new PsiDictionary();

					for(int j=i+1; j<opstack.size(); j++)
					{
						PsiStringlike key=(PsiStringlike)opstack.get(j++);
						PsiObject obj=opstack.get(j);
						dict.psiPut(key, obj);
					}
					opstack.setSize(i);
					opstack.push(dict);
					return;
				}
			}
			interpreter.error("unmatchedmark", this);
		}
		catch(ClassCastException|PsiException e)
		{
			interpreter.error(e, this);
		}
	}
}
