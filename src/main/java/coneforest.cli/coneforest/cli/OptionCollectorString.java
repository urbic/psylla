package coneforest.cli;

/**
*	An option holding a list of {@link String} values.
*/
public class OptionCollectorString
	extends OptionCollector<String>
{
	/**
	*	Creates new option with given names.
	*
	*	@param names space-delimited names.
	*/
	public OptionCollectorString(final String names)
	{
		super(names);
	}

	@Override
	public String parseArg(final String arg)
	{
		return arg;
	}
}
