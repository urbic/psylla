package coneforest.cli;

public class OptionCollectorInteger extends OptionCollector<Integer>
{
	public OptionCollectorInteger(final String names)
	{
		super(names);
	}

	public Integer parseArg(final String arg)
		throws ProcessingException
	{
		int result;
		try
		{
			result=Integer.parseInt(arg);
		}
		catch(final NumberFormatException e)
		{
			throw new ProcessingException(Messages.format("optProcExcpnBadArg", arg));
		}
		return result;
	}
}
