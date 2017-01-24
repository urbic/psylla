package coneforest.cli;

public class OptionLong extends OptionHolder<Long>
{
	public OptionLong(final String names)
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
