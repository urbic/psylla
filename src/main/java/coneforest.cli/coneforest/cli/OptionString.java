package coneforest.cli;

/**
*	An option holding a {@link String} value.
*/
public class OptionString
	extends OptionHolder<String>
{
	/**
	*	Creates new option with given names.
	*
	*	@param names space-delimited names.
	*/
	public OptionString(final String names)
	{
		super(names);
	}

	@Override
	public String parseArg(final String arg)
	{
		return arg;
	}
}
