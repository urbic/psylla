package coneforest.psi;

public class PsiString extends PsiStringlike
{
	public PsiString(String string)
	{
		setValue(string);
	}

	public PsiString(Token token)
	{
		if(token.kind==ParserConstants.TOKEN_STRING)
		{
			StringBuilder sb=new StringBuilder();
			for(int i=1; i<token.image.length()-1; i++)
			{
				char c=token.image.charAt(i);
				switch(c)
				{
					case '\\':
						i++;
						switch(token.image.charAt(i))
						{
							case '0':
								sb.append('\u0000');
								break;
							case 'n':
								sb.append('\n');
								break;
							case 'r':
								sb.append('\r');
								break;
							case 't':
								sb.append('\t');
								break;
							case 'f':
								sb.append('\f');
								break;
							case 'e':
								sb.append('\u001B');
								break;
							case '"':
								sb.append('"');
								break;
							case '\\':
								sb.append('\\');
								break;
							case 'u':
								sb.append(Character.toChars(Integer.valueOf(token.image.substring(i+1, i+5), 16)));
								i+=4;
								break;
						}
						break;
					default:
						sb.append(c);
						break;
				}
			}
			setValue(new String(sb));
		}
	}

	public String getTypeName() { return "string"; }

	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
		// TODO: executable strings
	}

	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		String string=getValue();
		for(int i=0; i<string.length(); i++)
		{
			char c=string.charAt(i);
			switch(c)
			{
				case '\u0000':
					sb.append("\\0");
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

}
