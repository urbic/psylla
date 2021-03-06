package coneforest.cli;

public class OptionCollectorLong extends OptionCollector<Long>
{
	public OptionCollectorLong(final String names)
	{
		super(names);
	}

	public Long parseArg(final String arg)
		throws ProcessingException
	{
		try
		{
			return Long.parseLong(arg);
		}
		catch(final NumberFormatException e)
		{
			throw new ProcessingException(Messages.format("optProcExcpnBadArg", arg));
		}
	}
}
