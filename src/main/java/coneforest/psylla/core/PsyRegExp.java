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
		throws PsyErrorException
	{
		try
		{
			pattern=java.util.regex.Pattern.compile(cs.toString());
		}
		catch(final java.util.regex.PatternSyntaxException ex)
		{
			throw new PsyInvalidRegExpException();
		}
	}

	public PsyRegExp(final PsyTextual oTextual)
		throws PsyErrorException
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

	public static PsyRegExp parseLiteral(final String image)
		throws PsyErrorException
	{
		final var sb=new StringBuilder();
		for(int i=1; i<image.length()-1; i++)
		{
			final var c=image.charAt(i);
			switch(c)
			{
				case '\\':
					i++;
					switch(image.charAt(i))
					{
						case '0':
							sb.append('\u0000');
							break;
						case 'a':
							sb.append('\u0007');
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
						case 'v':
							sb.append('\u000B');
							break;
						case 'f':
							sb.append('\f');
							break;
						case 'e':
							sb.append('\u001B');
							break;
						case '\n':
							break;
						case 'u':
							sb.append(Character.toChars(Integer.valueOf(image.substring(i+1, i+5), 16)));
							i+=4;
							break;
						case 'c':
							{
								final var ch=image.charAt(++i);
								sb.append(Character.toChars(ch+(ch<64? 64: -64)));
							}
							break;
						case 'x':
							try
							{
								// TODO: if not found
								final var j=image.indexOf('}', i+2);
								sb.append(Character.toChars(Integer.valueOf(image.substring(i+2, j), 16)));
								i=j;
							}
							catch(final IllegalArgumentException ex)
							{
								throw new PsySyntaxErrorException();
							}
							break;
						case 'N':
							try
							{
								final var j=image.indexOf('}', i+2);
								final var cp=Character.codePointOf(image.substring(i+2, j));
								// TODO
								sb.append((char)cp);
								i=j;
							}
							catch(final IllegalArgumentException ex)
							{
								throw new PsySyntaxErrorException();
							}
							break;
						/*
						case '\\':
						case 'd': case 'D':
						case 's': case 'S':
						case 'w': case 'W':
						case 'p':
						case 'b': case 'B':
						case 'A':
						case 'G':
						case 'z': case 'Z':
						case 'Q':
						case 'E':
						case '!':
						case '1': case '2': case '3':
						case '4': case '5': case '6':
						case '7': case '8': case '9':
						*/
						case '~':
							sb.append('~');
							break;
						default:
							sb.append("\\"+image.charAt(i));
							break;
					}
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return new PsyRegExp(sb);
	}

	private final java.util.regex.Pattern pattern;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyTextual>
				("regexp", PsyRegExp::new),
		};

}
