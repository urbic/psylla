package coneforest.cli;

public class OptionCollectorFloat extends OptionCollector<Float>
{
	public OptionCollectorFloat(String... names)
	{
		super(names);
	}

	public Float parseArg(String arg)
		throws CLIProcessingException
	{
		try
		{
			return Float.parseFloat(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException(Messages.format("optProcExcpnBadArg", arg));
		}
	}
}
