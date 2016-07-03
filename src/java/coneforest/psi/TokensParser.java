package coneforest.psi;
import coneforest.psi.core.*;

public class TokensParser
{
	public static PsiObject parseToken(final Token token)
		throws PsiException
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
				return new PsiReal(Double.parseDouble(token.image));
			case ParserConstants.NAME_SLASHED:
				return new PsiName(token.image.substring(1).intern());
			case ParserConstants.COMMAND:
				return new PsiCommand(token.image);
			default:
				throw new AssertionError();
		}
	}

	private static PsiString parseStringToken(final Token token)
		throws PsiSyntaxErrorException
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
							catch(IllegalArgumentException e)
							{
								throw new PsiSyntaxErrorException();
							}
							break;
					}
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return new PsiString(sb);
	}

	private static PsiName parseNameQuotedToken(final Token token)
		throws PsiSyntaxErrorException
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
							catch(IllegalArgumentException e)
							{
								throw new PsiSyntaxErrorException();
							}
							break;
					}
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return new PsiName(sb.toString().intern());
	}

	private static PsiRegExp parseRegExpToken(final Token token)
		throws PsiException
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
							catch(IllegalArgumentException e)
							{
								throw new PsiSyntaxErrorException();
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
						case '@':
							sb.append('@');
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
		return new PsiRegExp(sb);
	}

	private static PsiRealNumeric parseIntegerToken(final Token token)
		throws PsiSyntaxErrorException
	{
		try
		{
			try
			{
				return PsiInteger.valueOf(Long.parseLong(token.image));
			}
			catch(NumberFormatException e)
			{
				return new PsiReal(Double.parseDouble(token.image));
			}
		}
		catch(NumberFormatException e)
		{
			throw new PsiSyntaxErrorException();
		}
	}

	private static PsiInteger parseIntegerHexadecimalToken(final Token token)
		throws PsiSyntaxErrorException
	{
		try
		{
			if(token.image.startsWith("+") || token.image.startsWith("-"))
				return PsiInteger.valueOf(Long.parseLong(token.image.substring(0, 1)
						+token.image.substring(3), 16));
			else
				return PsiInteger.valueOf(Long.parseLong(token.image.substring(2), 16));
		}
		catch(NumberFormatException e)
		{
			throw new PsiSyntaxErrorException();
		}
	}

	private static PsiInteger parseIntegerBinaryToken(final Token token)
		throws PsiSyntaxErrorException
	{
		try
		{
			if(token.image.startsWith("+") || token.image.startsWith("-"))
				return PsiInteger.valueOf(Long.parseLong(token.image.substring(0, 1)
						+token.image.substring(3), 2));
			else
				return PsiInteger.valueOf(Long.parseLong(token.image.substring(2), 2));
		}
		catch(NumberFormatException e)
		{
			throw new PsiSyntaxErrorException();
		}
	}

	private static PsiInteger parseCharToken(final Token token)
		throws PsiSyntaxErrorException
	{
		switch(token.image.charAt(1))
		{
			case '\\':
				switch(token.image.charAt(2))
				{
					case '0':
						return PsiInteger.valueOf('\u0000');
					case 'a':
						return PsiInteger.valueOf('\u0007');
					case 'n':
						return PsiInteger.valueOf('\n');
					case 'r':
						return PsiInteger.valueOf('\r');
					case 't':
						return PsiInteger.valueOf('\t');
					case 'f':
						return PsiInteger.valueOf('\f');
					case 'e':
						return PsiInteger.valueOf('\u001B');
					case '\\':
						return PsiInteger.valueOf('\\');
					case 'u':
						return PsiInteger.valueOf(Integer.valueOf(token.image.substring(3, 7), 16));
					case 'c':
						{
							final int ch=token.image.charAt(3);
							return PsiInteger.valueOf(ch+(ch<64? 64: -64));
						}
					case 'x':
						try
						{
							return PsiInteger.valueOf(Integer.valueOf(
									token.image.substring(4, token.image.length()-1), 16));
						}
						catch(IllegalArgumentException e)
						{
							throw new PsiSyntaxErrorException();
						}
				}
			default:
				return PsiInteger.valueOf(token.image.charAt(1));
		}
	}

}
