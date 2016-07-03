package coneforest.cli;

public class OptionCollectorInteger extends OptionCollector<Integer>
{
	public OptionCollectorInteger(final String... names)
	{
		super(names);
	}

	public Integer parseArg(final String arg)
		throws CLIProcessingException
	{
		int result;
		try
		{
			result=Integer.parseInt(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException(Messages.format("optProcExcpnBadArg", arg));
		}
		return result;
	}
}
