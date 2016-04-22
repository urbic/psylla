package coneforest.cli;

/**
 *	Thrown when error occured during options processing.
 */
public class CLIProcessingException
	extends CLIException
{
	/**
	 * Constructor
	 * @param message message text
	 */
	public CLIProcessingException(String message)
	{
		super(Messages.format("optProcExcpn", message));
	}
}
