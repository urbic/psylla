package coneforest.psi;

abstract public class PsiObject
{
	abstract public String getTypeName();

	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	public void invoke(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	public byte getAccess()
	{
		return access;
	}

	public void setAccess(byte access)
	{
		this.access=access;
	}

	public boolean isReadable()
	{
		return (access&ACCESS_READ)!=0;
	}

	public boolean isWritable()
	{
		return (access&ACCESS_WRITE)!=0;
	}

	public boolean isExecutable()
	{
		return (access&ACCESS_EXECUTE)!=0;
	}

	public boolean isLiteral()
	{
		return !isExecutable();
	}

	public void setExecutable()
	{
		access|=ACCESS_EXECUTE;
	}

	public void setLiteral()
	{
		access&=~ACCESS_EXECUTE;
	}

	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(this==obj);
	}

	public PsiBoolean psiNe(final PsiObject obj)
	{
		return psiEq(obj).psiNot();
	}

	public PsiObject psiClone()
	{
		return this;
	}

	public PsiString psiToString()
	{
		return new PsiString(toString());
	}

	@Override
	public String toString()
	{
		return "-"+getTypeName()+"-";
	}

	private static final byte
		ACCESS_NOACCESS=0,
		ACCESS_EXECUTE=1,
		ACCESS_WRITE=2,
		ACCESS_READ=4;

	protected byte access=ACCESS_NOACCESS;
}
