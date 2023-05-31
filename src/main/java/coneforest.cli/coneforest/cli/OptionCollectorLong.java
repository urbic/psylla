package coneforest.cli;

/**
*	An option holding a list of {@link Long} values.
*/
public class OptionCollectorLong extends OptionCollector<Long>
{
	/**
	*	Creates new option with given names.
	*
	*	@param names space-delimited names.
	*/
	public OptionCollectorLong(final String names)
	{
		super(names);
	}

	@Override
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
