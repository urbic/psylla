package coneforest.cli;

import java.util.ArrayList;

/**
 * Base class representing an abstract option holding an object list.
 *
 * @param <T> class of containing object
 */
abstract public class OptionCollector<T> extends OptionWithArg
{
	public OptionCollector(String names)
	{
		super(names);
	}

	/**
	 * Returns array containing collected objects.
	 *
	 * @return array
	 */
	public T[] getValue()
	{
		return (T[])argList.toArray();
	}

	/**
	 * Add parsed argument to list. Argument is parsed
	 * by {@link #parseArg(String)}.
	 *
	 * @param arg argument
	 * @throws ProcessingException when parse error occured
	 */
	public void handle(final String arg)
		throws ProcessingException
	{
		argList.add(parseArg(arg));
	}

	/**
	 * Parses an argument and returns an object.
	 *
	 * @param arg argument
	 * @return object
	 * @throws ProcessingException when parse error occured
	 */
	abstract public T parseArg(final String arg)
		throws ProcessingException;

	private ArrayList<T> argList=new ArrayList<T>();
}
