package coneforest.cli;

public class OptionInteger extends OptionHolder<Integer>
{
	public OptionInteger(final String names)
	{
		super(names);
	}

	@Override
	public Integer parseArg(final String arg)
		throws ProcessingException
	{
		try
		{
			return Integer.parseInt(arg);
		}
		catch(final NumberFormatException e)
		{
			throw new ProcessingException(Messages.format("optProcExcpnBadArg", arg));
		}
	}
}
