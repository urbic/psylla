package coneforest.cli;

public class OptionLong extends OptionHolder<Long>
{
	public OptionLong(String names)
	{
		super(names);
	}

	public Long parseArg(String arg)
		throws ProcessingException
	{
		try
		{
			return Long.parseLong(arg);
		}
		catch(NumberFormatException e)
		{
			throw new ProcessingException("Bad option argument format: "+arg);
		}
	}
}
