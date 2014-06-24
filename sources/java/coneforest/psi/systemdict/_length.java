package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _length extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()==0)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj=opstack.pop();
			switch(obj.getType())
			{
				case TYPE_STRING:
				case TYPE_NAME:
					opstack.push(new PSIInteger(((String)obj.getValue()).length()));
					return;
				case TYPE_ARRAY:
					opstack.push(new PSIInteger(((PSIArray)obj).size()));
					return;
				case TYPE_DICTIONARY:
					opstack.push(new PSIInteger(((PSIDictionary)obj).size()));
					return;
				default:
					opstack.push(obj);
					interpreter.error("typecheck");
			}
		}
	}
}
