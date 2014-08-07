package coneforest.cli;

/**
 *	Thrown when error occured during options processing.
 */
public class ProcessingException extends Exception
{
	/**
	 * Constructor
	 * @param message message text
	 */
	public ProcessingException(String message)
	{
		System.err.println("Option processing exception: "+message);
	}
}
