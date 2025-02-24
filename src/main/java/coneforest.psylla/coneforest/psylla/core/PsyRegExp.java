package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
*	The representation of {@code regexp}.
*/
@Type("regexp")
public final class PsyRegExp
	implements PsyValue
{
	/**
	*	Context action of the {@code regexp} operator.
	*/
	@OperatorType("regexp")
	public static final ContextAction PSY_REGEXP
		=ContextAction.<PsyString>ofFunction(PsyRegExp::new);

	private final Pattern pattern;

	/**
	*	Constructs a new {@code regexp} from the character sequence.
	*
	*	@param cs the character sequence.
	*	@throws PsyInvalidRegExpException when a syntax error in a regular-expression pattern
	*	occured.
	*/
	public PsyRegExp(final CharSequence cs)
		throws PsyInvalidRegExpException
	{
		try
		{
			pattern=Pattern.compile(cs.toString());
		}
		catch(final PatternSyntaxException ex)
		{
			throw new PsyInvalidRegExpException(ex);
		}
	}

	/**
	*	Constructs a new {@code regexp} from the given {@code string}.
	*
	*	@param oName the given {@code string}.
	*	@throws PsyInvalidRegExpException when a syntax error in a regular-expression pattern
	*	occured.
	*/
	public PsyRegExp(final PsyString oName)
		throws PsyInvalidRegExpException
	{
		this(oName.stringValue());
	}

	/**
	*	{@return the pattern}
	*/
	public Pattern getPattern()
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
			final char c=patternImage.charAt(i);
			sb.append(switch(c)
				{
					case '\u0000'->"\\0";
					case '\u0007'->"\\a";
					case '\t'->"\\t";
					case '\u000B'->"\\v";
					case '\n'->"\\n";
					case '\f'->"\\f";
					case '\r'->"\\r";
					case '\u001B'->"\\e";
					case '~'->"\\~";
					default->c;
				});
		}
		sb.append('~');
		return sb.toString();
	}

	/**
	*	{@return the {@code string} object obtained as a result of parsing the literal token image}
	*
	*	@param image the literal token image.
	*	@throws PsySyntaxErrorException when the literal image is syntactically incorrect.
	*/
	public static PsyRegExp parseLiteral(final String image)
		throws PsyInvalidRegExpException, PsySyntaxErrorException
	{
		final var sb=new StringBuilder();
		for(int i=1; i<image.length()-1; i++)
		{
			final var c=image.charAt(i);
			switch(c)
			{
				case '\\'->
					{
						i++;
						switch(image.charAt(i))
						{
							case '0'->sb.append('\u0000');
							case 'a'->sb.append('\u0007');
							case 'n'->sb.append('\n');
							case 'r'->sb.append('\r');
							case 't'->sb.append('\t');
							case 'v'->sb.append('\u000B');
							case 'f'->sb.append('\f');
							case 'e'->sb.append('\u001B');
							case '\n'->{}
							case 'u'->
								{
									sb.append(Character.toChars(
											Integer.valueOf(image.substring(i+1, i+5), 16)));
									i+=4;
								}
							case 'c'->
								{
									final var ch=image.charAt(++i);
									sb.append(Character.toChars(ch+(ch<64? 64: -64)));
								}
							case 'x'->
								{
									try
									{
										// TODO: if not found
										final var j=image.indexOf('}', i+2);
										sb.append(Character.toChars(
												Integer.valueOf(image.substring(i+2, j), 16)));
										i=j;
									}
									catch(final IllegalArgumentException ex)
									{
										throw new PsySyntaxErrorException(ex);
									}
								}
							case 'N'->
								{
									try
									{
										// TODO: if not found
										final var j=image.indexOf('}', i+2);
										final var cp=Character.codePointOf(image.substring(i+2, j));
										// TODO
										sb.append((char)cp);
										i=j;
									}
									catch(final IllegalArgumentException ex)
									{
										throw new PsySyntaxErrorException(ex);
									}
								}
							case '~'->sb.append('~');
							default->sb.append("\\"+image.charAt(i));
						}
					}
				default->sb.append(c);
			}
		}
		return new PsyRegExp(sb);
	}
}
