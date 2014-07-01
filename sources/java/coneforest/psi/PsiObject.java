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

	public Object getValue()
	{
		return null;
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


	abstract public String toString();

	public static final byte
		ACCESS_NOACCESS=0,
		ACCESS_EXECUTE=1,
		ACCESS_WRITE=2,
		ACCESS_READ=4;

	protected byte access=ACCESS_NOACCESS;
	//private byte type;
}
