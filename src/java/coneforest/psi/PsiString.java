package coneforest.psi;

public class PsiString
	extends PsiStringlike
	implements PsiArraylike<PsiInteger>, PsiScalar<PsiString>
{

	public PsiString()
	{
		this("");
	}

	public PsiString(String string)
	{
		buffer=new StringBuffer(string);
	}

	public PsiString(StringBuffer buffer)
	{
		this.buffer=buffer;
	}

	public String getTypeName() { return "string"; }

	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
		// TODO: executable strings
	}

	public String getValue()
	{
		return buffer.toString();
	}

	public void setValue(final String value)
	{
		buffer.replace(0, value.length(), value);
	}

	public StringBuffer getBuffer()
	{
		return buffer;
	}

	public PsiInteger get(int index)
		throws PsiException
	{
		if(index>=0 && index<buffer.length())
			return new PsiInteger(buffer.charAt(index));
		else
			throw new PsiException("rangecheck");
	}

	public PsiInteger get(PsiInteger oIndex)
		throws PsiException
	{
		return get(oIndex.getValue().intValue());
	}

	public void put(int index, PsiInteger oChar)
		throws PsiException
	{
		if(index>=0 && index<buffer.length())
			buffer.setCharAt(index, (char)oChar.getValue().intValue());
		else
			throw new PsiException("rangecheck");
	}
	
	public void put(PsiInteger oIndex, PsiInteger oChar)
		throws PsiException
	{
		put(oIndex.getValue().intValue(), oChar);
	}

	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		String value=getValue();
		for(int i=0; i<value.length(); i++)
		{
			char c=value.charAt(i);
			switch(c)
			{
				case '\u0000':
					sb.append("\\0");
					break;
				case '\u0007':
					sb.append("\\a");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\u001B':
					sb.append("\\e");
					break;
				case '\"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				default:
					sb.append(c);
			}
		}
		return "\""+sb.toString()+"\"";
	}

	public static PsiString concatenation(PsiString x, PsiString y)
	{
		return new PsiString(x.getValue()+y.getValue());
	}
	
	public PsiBoolean lt(final PsiString string)
	{
		return new PsiBoolean(getValue().compareTo(string.getValue())<0);
	}

	public PsiBoolean le(final PsiString string)
	{
		return new PsiBoolean(getValue().compareTo(string.getValue())<=0);
	}

	public PsiBoolean gt(final PsiString string)
	{
		return new PsiBoolean(getValue().compareTo(string.getValue())>0);
	}

	public PsiBoolean ge(final PsiString string)
	{
		return new PsiBoolean(getValue().compareTo(string.getValue())>=0);
	}

	private StringBuffer buffer;
}
