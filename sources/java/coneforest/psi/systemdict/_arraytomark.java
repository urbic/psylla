package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _arraytomark extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
		{
			if(opstack.elementAt(i) instanceof PSIMark)
			{
				PSIArray array=new PSIArray();
				while(opstack.size()>i+1)
					array.add(0, opstack.pop());
				opstack.pop();
				opstack.push(array);
				return;
			}
		}
		interpreter.error("unmatchedmark");
	}
}
