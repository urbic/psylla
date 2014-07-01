package coneforest.psi;

public class PsiName extends PsiStringlike
{
	public PsiName(String value)
	{
		super(value);
	}

	public PsiName(Token token)
	{
		if(token.kind==ParserConstants.TOKEN_NAME_LITERAL)
		{
			setValue(token.image.substring(1));
			this.access=PsiObject.ACCESS_NOACCESS;
		}
		else if(token.kind==ParserConstants.TOKEN_NAME_EXECUTABLE)
		{
			setValue(token.image);
			this.access=PsiObject.ACCESS_EXECUTE;
		}
	}

	public String getTypeName() { return "name"; }

	public void execute(Interpreter interpreter)
	{
		if(isExecutable())
		{
			PsiObject obj=interpreter.getDictionaryStack().load(this);
			if(obj==null)
				interpreter.error("undefined");
			else if(obj instanceof PsiArray && ((PsiArray)obj).isExecutable())
			{
				//((PsiArray)obj).invoke(interpreter);
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
