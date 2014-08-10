package coneforest.cli;

public class OptionCollectorInteger extends OptionCollector<Integer>
{
	public OptionCollectorInteger(String names)
	{
		super(names);
	}

	public Integer parseArg(String arg)
		throws CLIProcessingException
	{
		int result;
		try
		{
			result=Integer.parseInt(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException("Bad option argument format: "+arg);
		}
		return result;
	}
}
