package coneforest.psi;

abstract public class PSIObject
{
	public void execute(PSIInterpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	public void invoke(PSIInterpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	public Object getValue()
	{
		return null;
	}

	abstract public byte getType();

	public String getTypeName()
	{
		return typeNames[getType()];
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
		TYPE_NULL=0,
		TYPE_BOOLEAN=1,
		TYPE_INTEGER=2,
		TYPE_REAL=3,
		TYPE_MARK=4,
		TYPE_NAME=5,
		TYPE_STRING=6,
		TYPE_ARRAY=7,
		TYPE_DICTIONARY=8,
		TYPE_FILE=9,
		TYPE_OPERATOR=10;

	public static final byte
		ACCESS_NOACCESS=0,
		ACCESS_EXECUTE=1,
		ACCESS_WRITE=2,
		ACCESS_READ=4;

	private static final String typeNames[]=
		{
			"null",
			"boolean",
			"integer",
			"real",
			"mark",
			"name",
			"string",
			"array",
			"dictionary",
			"file",
			"operator"
		};

	protected byte access=ACCESS_NOACCESS;
	//private byte type;
}
