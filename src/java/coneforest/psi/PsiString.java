package coneforest.psi;

public class PsiString extends PsiStringlike
{
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

	private StringBuffer buffer;
}
