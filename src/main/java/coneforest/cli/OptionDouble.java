package coneforest.cli;

/**
*	An option holding a {@link Double} value.
*/
public class OptionDouble
	extends OptionHolder<Double>
{
	/**
	*	Creates new option with given names.
	*
	*	@param names space-delimited names.
	*/
	public OptionDouble(final String names)
	{
		super(names);
	}

	@Override
	public Double parseArg(final String arg)
		throws ProcessingException
	{
		try
		{
			return Double.parseDouble(arg);
		}
		catch(final NumberFormatException e)
		{
			throw new ProcessingException(Messages.format("optProcExcpnBadArg", arg));
		}
	}
}
