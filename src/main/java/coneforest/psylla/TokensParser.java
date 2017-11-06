package coneforest.psylla;
import coneforest.psylla.core.*;

public class TokensParser
{
	public static PsyObject parseToken(final Token token)
		throws PsyException
	{
		switch(token.kind)
		{
			case ParserConstants.STRING:
				return TokensParser.parseStringToken(token);
			case ParserConstants.NAME_QUOTED:
				return TokensParser.parseNameQuotedToken(token);
			case ParserConstants.REGEXP:
				return TokensParser.parseRegExpToken(token);
			case ParserConstants.INTEGER:
				return TokensParser.parseIntegerToken(token);
			case ParserConstants.INTEGER_HEXADECIMAL:
				return TokensParser.parseIntegerHexadecimalToken(token);
			case ParserConstants.INTEGER_BINARY:
				return TokensParser.parseIntegerBinaryToken(token);
			case ParserConstants.CHAR:
				return TokensParser.parseCharToken(token);
			case ParserConstants.REAL:
				return new PsyReal(Double.parseDouble(token.image));
			case ParserConstants.NAME_SLASHED:
				return new PsyName(token.image.substring(1).intern());
			case ParserConstants.COMMAND:
				return new PsyCommand(token.image);
			default:
				throw new AssertionError();
		}
	}

	private static PsyString parseStringToken(final Token token)
		throws PsySyntaxErrorException
	{
		final StringBuilder sb=new StringBuilder();
		for(int i=1; i<token.image.length()-1; i++)
		{
			final char c=token.image.charAt(i);
			switch(c)
			{
				case '\\':
					i++;
					switch(token.image.charAt(i))
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
						case '"':
							sb.append('"');
							break;
						case '\\':
							sb.append('\\');
							break;
						case '\n':
							break;
						case 'u':
							sb.append(Character.toChars(Integer.valueOf(token.image.substring(i+1, i+5), 16)));
							i+=4;
							break;
						case 'c':
							{
								final int ch=token.image.charAt(++i);
								sb.append(Character.toChars(ch+(ch<64? 64: -64)));
							}
							break;
						case 'x':
							try
							{
								final int j=token.image.indexOf('}', i+2);
								sb.append(Character.toChars(Integer.valueOf(token.image.substring(i+2, j), 16)));
								i=j;
							}
							catch(final IllegalArgumentException e)
							{
								throw new PsySyntaxErrorException();
							}
							break;
					}
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return new PsyString(sb);
	}

	private static PsyName parseNameQuotedToken(final Token token)
		throws PsySyntaxErrorException
	{
		final StringBuilder sb=new StringBuilder();
		for(int i=1; i<token.image.length()-1; i++)
		{
			final char c=token.image.charAt(i);
			switch(c)
			{
				case '\\':
					i++;
					switch(token.image.charAt(i))
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
						case '\'':
							sb.append('\'');
							break;
						case '\\':
							sb.append('\\');
							break;
						case '\n':
							break;
						case 'u':
							sb.append(Character.toChars(Integer.valueOf(token.image.substring(i+1, i+5), 16)));
							i+=4;
							break;
						case 'c':
							{
								final int ch=token.image.charAt(++i);
								sb.append(Character.toChars(ch+(ch<64? 64: -64)));
							}
							break;
						case 'x':
							try
							{
								final int j=token.image.indexOf('}', i+2);
								sb.append(Character.toChars(Integer.valueOf(token.image.substring(i+2, j), 16)));
								i=j;
							}
							catch(final IllegalArgumentException e)
							{
								throw new PsySyntaxErrorException();
							}
							break;
					}
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return new PsyName(sb.toString().intern());
	}

	private static PsyRegExp parseRegExpToken(final Token token)
		throws PsyException
	{
		final StringBuilder sb=new StringBuilder();
		for(int i=1; i<token.image.length()-1; i++)
		{
			final char c=token.image.charAt(i);
			switch(c)
			{
				case '\\':
					i++;
					switch(token.image.charAt(i))
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
							sb.append(Character.toChars(Integer.valueOf(token.image.substring(i+1, i+5), 16)));
							i+=4;
							break;
						case 'c':
							{
								final int ch=token.image.charAt(++i);
								sb.append(Character.toChars(ch+(ch<64? 64: -64)));
							}
							break;
						case 'x':
							try
							{
								// TODO: if not found
								final int j=token.image.indexOf('}', i+2);
								sb.append(Character.toChars(Integer.valueOf(token.image.substring(i+2, j), 16)));
								i=j;
							}
							catch(final IllegalArgumentException e)
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
							sb.append("\\"+token.image.charAt(i));
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

	private static PsyRealNumeric parseIntegerToken(final Token token)
		throws PsySyntaxErrorException
	{
		try
		{
			try
			{
				return PsyInteger.valueOf(Long.parseLong(token.image));
			}
			catch(final NumberFormatException e)
			{
				return new PsyReal(Double.parseDouble(token.image));
			}
		}
		catch(final NumberFormatException e)
		{
			throw new PsySyntaxErrorException();
		}
	}

	private static PsyInteger parseIntegerHexadecimalToken(final Token token)
		throws PsySyntaxErrorException
	{
		try
		{
			if(token.image.startsWith("+") || token.image.startsWith("-"))
				return PsyInteger.valueOf(Long.parseLong(token.image.substring(0, 1)
						+token.image.substring(3), 16));
			else
				return PsyInteger.valueOf(Long.parseLong(token.image.substring(2), 16));
		}
		catch(final NumberFormatException e)
		{
			throw new PsySyntaxErrorException();
		}
	}

	private static PsyInteger parseIntegerBinaryToken(final Token token)
		throws PsySyntaxErrorException
	{
		try
		{
			if(token.image.startsWith("+") || token.image.startsWith("-"))
				return PsyInteger.valueOf(Long.parseLong(token.image.substring(0, 1)
						+token.image.substring(3), 2));
			else
				return PsyInteger.valueOf(Long.parseLong(token.image.substring(2), 2));
		}
		catch(final NumberFormatException e)
		{
			throw new PsySyntaxErrorException();
		}
	}

	private static PsyInteger parseCharToken(final Token token)
		throws PsySyntaxErrorException
	{
		switch(token.image.charAt(1))
		{
			case '\\':
				switch(token.image.charAt(2))
				{
					case '0':
						return PsyInteger.valueOf('\u0000');
					case 'a':
						return PsyInteger.valueOf('\u0007');
					case 'n':
						return PsyInteger.valueOf('\n');
					case 'r':
						return PsyInteger.valueOf('\r');
					case 't':
						return PsyInteger.valueOf('\t');
					case 'v':
						return PsyInteger.valueOf('\u000B');
					case 'f':
						return PsyInteger.valueOf('\f');
					case 'e':
						return PsyInteger.valueOf('\u001B');
					case '\\':
						return PsyInteger.valueOf('\\');
					case 'u':
						return PsyInteger.valueOf(Integer.valueOf(token.image.substring(3, 7), 16));
					case 'c':
						{
							final int ch=token.image.charAt(3);
							return PsyInteger.valueOf(ch+(ch<64? 64: -64));
						}
					case 'x':
						try
						{
							return PsyInteger.valueOf(Integer.valueOf(
									token.image.substring(4, token.image.length()-1), 16));
						}
						catch(final IllegalArgumentException e)
						{
							throw new PsySyntaxErrorException();
						}
				}
			default:
				return PsyInteger.valueOf(token.image.charAt(1));
		}
	}
}
