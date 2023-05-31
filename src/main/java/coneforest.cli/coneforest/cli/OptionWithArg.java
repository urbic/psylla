package coneforest.cli;

/**
*	An abstract option with argument.
*/
abstract public class OptionWithArg<T>
	extends Option
{
	/**
	*	Creates new option with given names.
	*
	*	@param names space-delimited names.
	*/
	public OptionWithArg(final String names)
	{
		super(names);
	}

	/**
	*	Option argument is passed to this method.
	*
	*	@param arg argument
	*	@throws ProcessingException when an error while handling the argument.
	*/
	abstract public void handle(final String arg)
		throws ProcessingException;
}
