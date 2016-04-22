package coneforest.cli;

import java.util.ArrayList;

/**
 * Base class representing an abstract option holding an object list.
 *
 * @param <T> a type of containing object
 */
abstract public class OptionCollector<T>
	extends OptionWithArg
{
	public OptionCollector(final String... names)
	{
		super(names);
	}

	/**
	 * Returns array containing collected objects.
	 *
	 * @return array
	 */
	@Override
	public T[] getValue()
	{
		return (T[])argList.toArray();
	}

	/**
	 * Add parsed argument to list. Argument is parsed
	 * by {@link #parseArg(String)}.
	 *
	 * @param arg argument
	 * @throws CLIProcessingException when parse error occured
	 */
	@Override
	public void handle(final String arg)
		throws CLIProcessingException
	{
		argList.add(parseArg(arg));
	}

	/**
	 * Parses an argument and returns an object.
	 *
	 * @param arg argument
	 * @return object
	 * @throws CLIProcessingException when parse error occured
	 */
	abstract public T parseArg(final String arg)
		throws CLIProcessingException;

	private ArrayList<T> argList=new ArrayList<T>();
}
