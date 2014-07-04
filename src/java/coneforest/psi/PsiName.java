package coneforest.psi;

public class PsiName extends PsiStringlike
{
	public PsiName(String value)
	{
		super(value);
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value=value;
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

	private String value;

}
