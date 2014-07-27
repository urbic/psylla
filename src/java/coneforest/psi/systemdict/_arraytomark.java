package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _arraytomark extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
		{
			if(opstack.get(i) instanceof PsiMark)
			{
				PsiArray array=new PsiArray();
				while(opstack.size()>i+1)
					array.add(0, opstack.pop());
				opstack.pop();
				opstack.push(array);
				return;
			}
		}
		interpreter.error("unmatchedmark", this);
	}
}
