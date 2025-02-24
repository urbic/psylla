package coneforest.psylla.runtime;

import java.util.ResourceBundle;

/**
*	Support for I18N.
*/
public interface Messages
{

	public static final ResourceBundle MESSAGES
		=ResourceBundle.getBundle(Messages.class.getName());

	/**
	*	{@return a string for the given key}
	*
	*	@param key the key for the desired string.
	*/
	public static String getString(final String key)
	{
		return MESSAGES.getString(key);
	}

	/**
	*	{@return a formatted string using the format string for the given key and arguments}
	*
	*	@param key the key for the desired format string.
	*	@param args arguments referenced by the format specifiers in the format string.
	*/
	public static String format(final String key, final Object... args)
	{
		return String.format(getString(key), args);
	}
}
