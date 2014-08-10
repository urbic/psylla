package coneforest.cli;

/**
 * Thrown when error occured during parser configuration.
 */
public class CLIConfigurationException
	extends CLIException
{
	/**
	 * Constructor
	 * @param message message text
	 */
	public CLIConfigurationException(String message)
	{
		super("Option configuration exception: "+message);
	}
}
