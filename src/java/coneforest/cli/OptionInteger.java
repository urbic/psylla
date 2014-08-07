package coneforest.cli;

public class OptionInteger extends OptionHolder<Integer>
{
	public OptionInteger(String names)
	{
		super(names);
	}

	public Integer parseArg(String arg)
		throws ProcessingException
	{
		try
		{
			return Integer.parseInt(arg);
		}
		catch(NumberFormatException e)
		{
			throw new ProcessingException("Bad option argument format: "+arg);
		}
	}
}
