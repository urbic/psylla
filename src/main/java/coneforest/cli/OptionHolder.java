package coneforest.cli;

/**
*	Base class representing an abstract option holding an object.
*
*	@param <T> class of containing object.
*/
abstract public class OptionHolder<T>
	extends OptionWithArg<T>
{
	public OptionHolder(final String names)
	{
		super(names);
	}

	/**
	*	Returns containing object.
	*
	*	@return an object.
	*/
	@Override
	public T getValue()
	{
		return value;
	}

	/**
	*	Replaces current option value with parsed argument. Argument is parsed
	*	by {@link #parseArg(String)}.
	*
	*	@param arg an argument,
	*	@throws ProcessingException when parse error occurs.
	*/
	@Override
	public void handle(final String arg)
		throws ProcessingException
	{
		value=parseArg(arg);
	}

	/**
	*	Parses an argument and returns an object.
	*
	*	@param arg an argument.
	*	@return an object.
	*	@throws ProcessingException when parse error occurs.
	*/
	abstract public T parseArg(final String arg)
		throws ProcessingException;

	private T value;
}
