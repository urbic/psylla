package coneforest.cli;

/**
 * Base class representing an abstract option with argument.
 */
abstract public class OptionWithArg<T>
	extends Option
{
	public OptionWithArg(final String... names)
	{
		super(names);
	}

	/**
	 * Option argument is passed to this method.
	 *
	 * @param arg argument
	 * @throws CLIProcessingException
	 */
	abstract public void handle(String arg)
		throws CLIProcessingException;
}
