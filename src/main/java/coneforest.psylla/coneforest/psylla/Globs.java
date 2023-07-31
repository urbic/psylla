package coneforest.psylla;

/**
*	Support for shell-like glob patterns.
*/
public class Globs
{
	/**
	*	The {@code '?'} character.
	*/
	public static final char SINGULAR_PATTERN='?';

	/**
	*	The {@code '*'} character.
	*/
	public static final char PLURAL_PATTERN='*';

	public static boolean matches(final String pattern, final String string,
			final char singular, final char plural)
	{
		if(string.isEmpty())
		{
			if(pattern.isEmpty())
				return true;
			if(pattern.charAt(0)==singular)
				return false;
			if(pattern.charAt(0)==plural)
				return matches(pattern.substring(1), string, singular, plural);
			return false;
		}
		else
		{
			if(pattern.isEmpty())
				return false;
			if(pattern.charAt(0)==singular)
				return matches(pattern.substring(1), string.substring(1), singular, plural);
			if(pattern.charAt(0)==plural)
				return matches(pattern.substring(1), string, singular, plural)
						|| matches(pattern, string.substring(1), singular, plural);
			return pattern.charAt(0)==string.charAt(0)
					&& matches(pattern.substring(1), string.substring(1), singular, plural);
		}
	}

	public static boolean matches(final String pattern, final String string)
	{
		return matches(pattern, string, SINGULAR_PATTERN, PLURAL_PATTERN);
	}
}
