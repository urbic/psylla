package coneforest.psi;

public class PSIName extends PSIStringlike
{
	public PSIName(String value)
	{
		super(value);
	}

	public PSIName(Token token)
	{
		if(token.kind==PSIParserConstants.TOKEN_NAME_LITERAL)
		{
			setValue(token.image.substring(1));
			this.access=PSIObject.ACCESS_NOACCESS;
		}
		else if(token.kind==PSIParserConstants.TOKEN_NAME_EXECUTABLE)
		{
			setValue(token.image);
			this.access=PSIObject.ACCESS_EXECUTE;
		}
	}

	public String getTypeName() { return "name"; }

	public void execute(PSIInterpreter interpreter)
	{
		if(isExecutable())
		{
			PSIObject obj=interpreter.getDictionaryStack().load(this);
			if(obj==null)
				interpreter.error("undefined");
			else if(obj instanceof PSIArray && ((PSIArray)obj).isExecutable())
			{
				//((PSIArray)obj).invoke(interpreter);
				obj.invoke(interpreter);
			}
			else
				obj.execute(interpreter);

		}
		else
			interpreter.getOperandStack().push(this);
	}

	public String toString()
	{
		return (isExecutable()? "": "/")+getValue();
	}

}
