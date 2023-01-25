package coneforest.cli;

/**
*	An option holding a {@link Integer} value.
*/
public class OptionInteger extends OptionHolder<Integer>
{
	/**
	*	Creates new option with given names.
	*
	*	@param names space-delimited names.
	*/
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
