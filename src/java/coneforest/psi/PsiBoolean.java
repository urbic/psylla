package coneforest.psi;

public class PsiBoolean extends PsiObject
{
	public PsiBoolean(boolean value)
	{
		this.value=value;
	}

	public String getTypeName() { return "boolean"; }
	
	public Boolean getValue()
	{
		return value;
	}

	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	public String toString()
	{
		return value? "true": "false";
	}

	static public PsiBoolean not(PsiBoolean p)
	{
		return new PsiBoolean(!p.getValue());
	}

	static public PsiBoolean and(PsiBoolean p, PsiBoolean q)
	{
		return new PsiBoolean(p.getValue() && q.getValue());
	}

	static public PsiBoolean or(PsiBoolean p, PsiBoolean q)
	{
		return new PsiBoolean(p.getValue() || q.getValue());
	}

	static public PsiBoolean xor(PsiBoolean p, PsiBoolean q)
	{
		return new PsiBoolean(p.getValue() ^ q.getValue());
	}

	private boolean value;
}
