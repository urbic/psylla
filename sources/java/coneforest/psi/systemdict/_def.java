package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _def extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj=opstack.pop();
			PSIObject name=opstack.pop();
			switch(name.getType())
			{
				case TYPE_STRING:
				case TYPE_NAME:
					//DictionaryStack dictstack=interpreter.getDictionaryStack();
					PSIDictionary currentdict=interpreter.getDictionaryStack().peek();
					currentdict.put((String)name.getValue(), obj);
					// TODO: dictfull, invalidaccess, limitcheck, VMerror
					break;
				default:
					interpreter.error("typecheck");
			}
		}
	}
}
