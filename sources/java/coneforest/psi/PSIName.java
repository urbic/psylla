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

	public byte getType()
	{
		return TYPE_NAME;
	}

	public void execute(PSIInterpreter interpreter)
	{
		if(isExecutable())
		{
			//interpreter.getDictionaryStack().load(this).execute(interpreter);
			PSIObject obj=interpreter.getDictionaryStack().load(this);
			obj.invoke(interpreter);
		}
		else
			interpreter.getOperandStack().push(this);
	}

	public String toString()
	{
		return (isExecutable()? "": "/")+getValue();
	}

}
