package coneforest.cli;

public class Messages
{
	public static String getString(String id)
	{
		return messages.getString(id);
	}

	public static String format(String id, Object... args)
	{
		return String.format(getString(id), args);
	}

	private static final java.util.ResourceBundle messages
		=java.util.ResourceBundle.getBundle("coneforest.cli.Messages");
}
