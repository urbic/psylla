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
	 * @return value
  	 */
	abstract public Object getValue();

	/**
	 * Returns true if given name is found among option names, and false
	 * otherwise.
	 *
	 * @return result
  	 */
	public boolean hasName(final String name)
	{
		return names.contains(name);
	}

	private final java.util.HashSet<String> names;
}
