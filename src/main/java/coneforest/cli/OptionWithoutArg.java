package coneforest.cli;

/**
*	Base class representing an abstract option without argument.
*/
abstract public class OptionWithoutArg<T>
	extends Option
{
	public OptionWithoutArg(final String names)
	{
		super(names);
	}

	/**
	*	Called when option is processed.
	*/
	abstract public void handle();
}
