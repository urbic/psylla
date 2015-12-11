package coneforest.cli;

/**
 * Base class representing an abstract option.
 */
abstract public class Option
{
	public Option(final String... names)
	{
		this.names=new java.util.HashSet<String>();
		for(String name: names)
			if(!name.equals(""))
				this.names.add(name);
	}

	/**
	 * Abstract method for fetching an option value.
	 *
	 * @param <T> a type of a value
	 * @return a value
  	 */
	abstract public <T> T getValue();

	/**
	 * Returns true if given name is found among option names, and false
	 * otherwise.
	 *
	 * @param name a name
	 * @return result
  	 */
	public boolean hasName(final String name)
	{
		return names.contains(name);
	}

	private final java.util.HashSet<String> names;
}
