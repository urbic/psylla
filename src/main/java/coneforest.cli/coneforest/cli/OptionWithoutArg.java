package coneforest.cli;

/**
*	An abstract option without argument.
*/
abstract public class OptionWithoutArg<T>
	extends Option
{
	/**
	*	Creates new option with given names.
	*
	*	@param names space-delimited names.
	*/
	public OptionWithoutArg(final String names)
	{
		super(names);
	}

	/**
	*	Called when option is processed.
	*/
	abstract public void handle();
}
