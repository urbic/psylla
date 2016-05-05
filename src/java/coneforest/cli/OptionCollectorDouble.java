package coneforest.cli;

public class OptionCollectorDouble extends OptionCollector<Double>
{
	public OptionCollectorDouble(final String... names)
	{
		super(names);
	}

	public Double parseArg(final String arg)
		throws CLIProcessingException
	{
		try
		{
			return Double.parseDouble(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException(Messages.format("optProcExcpnBadArg", arg));
		}
	}
}
