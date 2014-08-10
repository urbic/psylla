package coneforest.cli;

public class OptionString
	extends OptionHolder<String>
{
	public OptionString(String... names)
	{
		super(names);
	}

	public String parseArg(String arg)
	{
		return arg;
	}
}
