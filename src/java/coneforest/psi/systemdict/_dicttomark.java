package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _dicttomark extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
		{
			if(opstack.get(i) instanceof PsiMark)
			{
				if(opstack.size()-1-i % 2==1)
				{
					interpreter.error("rangecheck");
					return;
				}
				PsiDictionary dict=new PsiDictionary();
				while(opstack.size()>i+1)
				{
					PsiObject obj=opstack.pop();
					PsiObject key=opstack.pop();
					if(key instanceof PsiStringlike)
						dict.put((PsiStringlike)key, obj);
					else
					{
						interpreter.error("typecheck");
						return;
					}
				}
				opstack.pop();
				opstack.push(dict);
				return;
			}
		}
		interpreter.error("unmatchedmark");
	}
}
