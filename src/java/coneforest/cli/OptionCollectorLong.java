package coneforest.cli;

public class OptionCollectorLong extends OptionCollector<Long>
{
	public OptionCollectorLong(final String... names)
	{
		super(names);
	}

	public Long parseArg(final String arg)
		throws CLIProcessingException
	{
		long result;
		try
		{
			result=Long.parseLong(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException(Messages.format("optProcExcpnBadArg", arg));
		}
		return result;
	}
}
