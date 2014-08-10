package coneforest.cli;

/**
 * Thrown when error occured during parser configuration.
 */
public class CLIException extends Exception
{
	/**
	 * Constructor
	 * @param message message text
	 */
	public CLIException(String message)
	{
		super(message);
	}
}
