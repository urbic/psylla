package coneforest.cli;

/**
 * Thrown when error occured during parser configuration.
 */
public class ConfigurationException extends Exception
{
	/**
	 * Constructor
	 * @param message message text
	 */
	public ConfigurationException(String message)
	{
		System.err.println("Option configuration exception: "+message);
	}
}
