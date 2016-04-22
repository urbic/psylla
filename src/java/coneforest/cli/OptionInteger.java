package coneforest.cli;

public class OptionInteger extends OptionHolder<Integer>
{
	public OptionInteger(final String... names)
	{
		super(names);
	}

	@Override
	public Integer parseArg(final String arg)
		throws CLIProcessingException
	{
		try
		{
			return Integer.parseInt(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException(Messages.format("optProcExcpnBadArg", arg));
		}
	}
}
