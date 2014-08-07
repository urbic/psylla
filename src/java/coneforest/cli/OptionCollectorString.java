package coneforest.cli;

public class OptionCollectorString extends OptionCollector<String>
{
	public OptionCollectorString(String names)
	{
		super(names);
	}

	public String parseArg(String arg)
	{
		return arg;
	}
}
