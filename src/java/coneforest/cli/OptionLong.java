package coneforest.cli;

public class OptionLong extends OptionHolder<Long>
{
	public OptionLong(final String... names)
	{
		super(names);
	}

	public Long parseArg(final String arg)
		throws CLIProcessingException
	{
		try
		{
			return Long.parseLong(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException(Messages.format("optProcExcpnBadArg", arg));
		}
	}
}
