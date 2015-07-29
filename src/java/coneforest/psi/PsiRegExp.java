package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">regexp</code> object.
 */
public class PsiRegExp
	implements PsiObject
{
	public PsiRegExp(CharSequence value)
		throws PsiException
	{
		try
		{
			pattern=java.util.regex.Pattern.compile(value.toString());
		}
		catch(java.util.regex.PatternSyntaxException e)
		{
			throw new PsiException("invalidregexp");
		}
	}

	public PsiRegExp(PsiStringlike stringlike)
		throws PsiException
	{
		this(stringlike.getString());
	}

	/**
	 *	@return a string <code class="constant">"regexp"</code>.
	 */
	public String getTypeName()
	{
		return "regexp";
	}

	public java.util.regex.Pattern getPattern()
	{
		return pattern;
	}

	@Override
	public String toString()
	{
		/*
		StringBuilder sb=new StringBuilder();
		String string=pattern.toString();
		for(int i=0; i<string.length(); i++)
		{
			char c=string.charAt(i);
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
				case '!':
					sb.append("\\!");
					break;
				case '\\':
					{
						char ch=string.charAt(++i);
						switch(ch)
						{
							case '\\':
							case 'd':
							case 'D':
							case 's':
							case 'S':
							case 'w':
							case 'W':
								sb.append("\\");
								sb.append(ch);
								break;
						}
					}
					//sb.append("\\\\");
					break;
				default:
					sb.append(c);
			}
		}
		*/
		//return "!"+sb.toString()+"!";
		StringBuilder sb=new StringBuilder("@");
		String patternImage=pattern.toString();
		for(int i=0; i<patternImage.length(); i++)
		{
			if(patternImage.charAt(i)=='@')
				sb.append('\\');
			sb.append(patternImage.charAt(i));
		}
		sb.append('@');
		return sb.toString();
	}

	private java.util.regex.Pattern pattern;
}
