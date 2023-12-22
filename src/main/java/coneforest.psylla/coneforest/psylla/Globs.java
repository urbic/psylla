package coneforest.psylla;

/**
*	Support for shell-like glob patterns.
*/
public interface Globs
{
	/**
	*	The character denoting singular quantifier ({@value}).
	*/
	public static final char QUANTIFIER_SINGULAR='?';

	/**
	*	The character denoting plural quantifier ({@value}).
	*/
	public static final char QUANTIFIER_PLURAL='*';

	/**
	*	Checks if the string matches the given pattern using the given characters denoting singular
	*	and plural quantifiers.
	*
	*	@param pattern the pattern.
	*	@param string the string.
	*	@param singular the character denoting singular quantifier.
	*	@param plural the character denoting plural quantifier.
	*	@return {@code true} if the string matches the pattern, {@code false} otherwise.
	*/
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

	/**
	*	Checks if the string matches the given pattern using default quantifier notation ({@value
	*	QUANTIFIER_SINGULAR} for singular, {@value QUANTIFIER_PLURAL} for plural).
	*
	*	@param pattern the pattern.
	*	@param string the string.
	*	@return {@code true} if the string matches the pattern, {@code false} otherwise.
	*/
	public static boolean matches(final String pattern, final String string)
	{
		return matches(pattern, string, QUANTIFIER_SINGULAR, QUANTIFIER_PLURAL);
	}
}
