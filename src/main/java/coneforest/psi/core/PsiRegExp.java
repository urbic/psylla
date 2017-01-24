package coneforest.psi.core;

/**
*	A representation of Ψ-{@code regexp} object.
*/
public class PsiRegExp
	implements PsiAtomic
{
	public PsiRegExp(final CharSequence cs)
		throws PsiException
	{
		try
		{
			pattern=java.util.regex.Pattern.compile(cs.toString());
		}
		catch(final java.util.regex.PatternSyntaxException e)
		{
			throw new PsiInvalidRegExpException();
		}
	}

	public PsiRegExp(final PsiStringy oStringy)
		throws PsiException
	{
		this(oStringy.stringValue());
	}

	/**
	*	@return a string {@code "regexp"}.
	*/
	public String typeName()
	{
		return "regexp";
	}

	public java.util.regex.Pattern getPattern()
	{
		return pattern;
	}

	@Override
	public String toSyntaxString()
	{
		final StringBuilder sb=new StringBuilder("~");
		final String patternImage=pattern.toString();
		for(int i=0; i<patternImage.length(); i++)
		{
			char c=patternImage.charAt(i);
			switch(c)
			{
				case '\u0000':
					sb.append("\\0");
					break;
				case '\u0007':
					sb.append("\\a");
					break;
				case '\u0009':
					sb.append("\\t");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\u001B':
					sb.append("\\e");
					break;
				case '~':
					sb.append("\\~");
					break;
				default:
					sb.append(c);
					break;
			}
		}
		sb.append('~');
		return sb.toString();
	}

	private final java.util.regex.Pattern pattern;
}
