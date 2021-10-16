package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code regexp} object.
*/
@Type("regexp")
public class PsyRegExp
	implements PsyAtomic
{
	public PsyRegExp(final CharSequence cs)
		throws PsyException
	{
		try
		{
			pattern=java.util.regex.Pattern.compile(cs.toString());
		}
		catch(final java.util.regex.PatternSyntaxException e)
		{
			throw new PsyInvalidRegExpException();
		}
	}

	public PsyRegExp(final PsyTextual oTextual)
		throws PsyException
	{
		this(oTextual.stringValue());
	}

	public java.util.regex.Pattern getPattern()
	{
		return pattern;
	}

	@Override
	public String toSyntaxString()
	{
		final var sb=new StringBuilder("~");
		final var patternImage=pattern.toString();
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
				case '\t':
					sb.append("\\t");
					break;
				case '\u000B':
					sb.append("\\v");
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

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyTextual>
				("regexp", PsyRegExp::new),
		};

}