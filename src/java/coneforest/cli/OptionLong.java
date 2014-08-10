package coneforest.cli;

public class OptionLong extends OptionHolder<Long>
{
	public OptionLong(String names)
	{
		super(names);
	}

	public Long parseArg(String arg)
		throws CLIProcessingException
	{
		try
		{
			return Long.parseLong(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException("Bad option argument format: "+arg);
		}
	}
}
