package coneforest.psylla;

import java.util.ResourceBundle;

public class Messages
{
	public static String getString(final String id)
	{
		return messages.getString(id);
	}

	public static String format(final String id, final Object... args)
	{
		return String.format(getString(id), args);
	}

	private static final ResourceBundle messages
		=ResourceBundle.getBundle(coneforest.psylla.Messages.class.getName());
}
