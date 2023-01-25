package coneforest.cli;

/**
*	An option holding a {@link Boolean} value.
*/
public class OptionBoolean extends OptionHolder<Boolean>
{
	public OptionBoolean(final String names)
	{
		super(names);
	}

	public Boolean parseArg(final String arg)
	{
		return Boolean.parseBoolean(arg);
	}
}
