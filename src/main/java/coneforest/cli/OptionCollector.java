package coneforest.cli;

/**
*	Base class representing an abstract option holding an object list.
*
*	@param <T> a type of containing object
*/
abstract public class OptionCollector<T>
	extends OptionWithArg
{
	public OptionCollector(final String names)
	{
		super(names);
	}

	/**
	*	Returns array containing collected objects.
	*
	*	@return an array.
	*/
	@Override
	public T[] getValue()
	{
		return (T[])argList.toArray();
	}

	/**
	*	Add parsed argument to list. Argument is parsed by {@link
	*	#parseArg(String)}.
	*
	*	@param arg an argument.
	*	@throws ProcessingException when parse error occurs.
	*/
	@Override
	public void handle(final String arg)
		throws ProcessingException
	{
		argList.add(parseArg(arg));
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

	private final java.util.ArrayList<T> argList
		=new java.util.ArrayList<T>();
}
