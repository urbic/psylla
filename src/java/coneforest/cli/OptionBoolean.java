package coneforest.cli;

public class OptionBoolean extends OptionHolder<Boolean>
{
	public OptionBoolean(String names)
	{
		super(names);
	}

	public Boolean parseArg(String arg)
	{
		return Boolean.parseBoolean(arg);
	}
}
