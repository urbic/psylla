package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _dicttomark extends PSIOperator
{
	public String getName()	{ return "dicttomark"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		for(int i=opstack.size()-1; i>=0; i--)
		{
			if(opstack.elementAt(i) instanceof PSIMark)
			{
				if(opstack.size()-1-i % 2==1)
				{
					interpreter.error("rangecheck");
					return;
				}
				PSIDictionary dict=new PSIDictionary();
				while(opstack.size()>i+1)
				{
					PSIObject obj=opstack.pop();
					PSIObject key=opstack.pop();
					if(key instanceof PSIStringlike)
						dict.put((PSIStringlike)key, obj);
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
