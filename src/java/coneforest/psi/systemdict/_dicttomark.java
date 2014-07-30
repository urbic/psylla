package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _dicttomark extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
		{
			if(opstack.get(i) instanceof PsiMark)
			{
				if(opstack.size()-1-i % 2==1)
				{
					interpreter.error("rangecheck", this);
					return;
				}
				PsiDictionary dict=new PsiDictionary();
				while(opstack.size()>i+1)
				{
					PsiObject obj=opstack.pop();
					PsiObject key=opstack.pop();
					if(key instanceof PsiAbstractStringlike)
						dict.psiPut((PsiAbstractStringlike)key, obj);
					else
					{
						interpreter.error("typecheck", this);
						return;
					}
				}
				opstack.pop();
				opstack.push(dict);
				return;
			}
		}
		interpreter.error("unmatchedmark", this);
	}
}
