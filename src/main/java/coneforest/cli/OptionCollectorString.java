package coneforest.cli;

/**
*	An option holding a list of {@link java.lang.String} values.
*/
public class OptionCollectorString
	extends OptionCollector<String>
{
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
