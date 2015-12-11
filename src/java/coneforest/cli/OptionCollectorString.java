package coneforest.cli;

public class OptionCollectorString
	extends OptionCollector<String>
{
	public OptionCollectorString(String names)
	{
		super(names);
	}

	@Override
	public String parseArg(String arg)
	{
		return arg;
	}
}
