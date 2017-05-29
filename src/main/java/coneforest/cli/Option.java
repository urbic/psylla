package coneforest.cli;

/**
*	Base class representing an abstract option.
*/
abstract public class Option
{
	/**
	*	Constructs an option object with given names.
	*
	*	@param names option names.
	*/
	public Option(final String names)
	{
		for(String name: names.split(" "))
			if(!name.isEmpty())
				this.names.add(name);
	}

	/**
	*	Abstract method for fetching an option value.
	*
	*	@param <T> a type of a value.
	*	@return a value.
  	*/
	abstract public <T> T getValue();

	/**
	*	Returns true if given name is found among option names, and false
	*	otherwise.
	*
	*	@param name a name.
	*	@return a result.
  	*/
	public boolean hasName(final String name)
	{
		return names.contains(name);
	}

	private final java.util.HashSet<String> names
		=new java.util.HashSet<String>();
}
