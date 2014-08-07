package coneforest.cli;

/**
 * Base class representing an abstract option with argument.
 */
abstract public class OptionWithArg extends Option
{
	public OptionWithArg(String names)
	{
		super(names);
	}

	/**
	 * Option argument is passed to this method.
	 *
	 * @param arg argument
	 * @throws ProcessingException
	 */
	abstract public void handle(String arg)
		throws ProcessingException;
}
