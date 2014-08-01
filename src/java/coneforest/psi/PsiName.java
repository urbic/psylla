package coneforest.psi;

public class PsiName
	extends PsiAbstractStringlike
{
	public PsiName(String name)
	{
		this.name=name;
	}

	public PsiName(PsiStringlike stringlike)
	{
		this(stringlike.getString());
	}

	@Override
	public String getString()
	{
		return name;
	}

	/*
	@Override
	public void setValue(String name)
	{
		this.name=name;
	}
	*/

	@Override
	public String getTypeName() { return "name"; }

	public void execute(Interpreter interpreter)
	{
		if(isExecutable())
		{
			/*
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
			*/
			try
			{
				interpreter.getDictionaryStack().load(this).invoke(interpreter);
			}
			catch(PsiException e)
			{
				interpreter.error(e.kind());
			}
		}
		else
			interpreter.getOperandStack().push(this);
	}

	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(name.length());
	}

	@Override
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(name.length()==0);
	}

	@Override
	public String toString()
	{
		return (isExecutable()? "": "/")+getString();
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof PsiName
				&& psiEq((PsiName)object).getValue();
	}

	private final String name;
}
