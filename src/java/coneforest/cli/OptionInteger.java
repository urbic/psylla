package coneforest.cli;

public class OptionInteger extends OptionHolder<Integer>
{
	public OptionInteger(String names)
	{
		super(names);
	}

	@Override
	public Integer parseArg(String arg)
		throws CLIProcessingException
	{
		try
		{
			return Integer.parseInt(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException("Bad option argument format: "+arg);
		}
	}
}
