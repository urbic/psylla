package coneforest.cli;

public class OptionDouble
	extends OptionHolder<Double>
{
	public OptionDouble(final String... names)
	{
		super(names);
	}

	@Override
	public Double parseArg(final String arg)
		throws CLIProcessingException
	{
		double result;
		try
		{
			result=Double.parseDouble(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException(Messages.format("optProcExcpnBadArg", arg));
		}
		return result;
	}
}
