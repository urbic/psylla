package coneforest.cli;

public class OptionString
	extends OptionHolder<String>
{
	public OptionString(final String... names)
	{
		super(names);
	}

	@Override
	public String parseArg(final String arg)
	{
		return arg;
	}
}
