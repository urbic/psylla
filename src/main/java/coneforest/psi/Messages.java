package coneforest.psi;

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

	private static final java.util.ResourceBundle messages
		=java.util.ResourceBundle.getBundle("coneforest.psi.Messages");
}
