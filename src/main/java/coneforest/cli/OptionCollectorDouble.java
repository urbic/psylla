package coneforest.cli;

/**
*	An option holding a list of {@link java.lang.Double} values.
*/
public class OptionCollectorDouble extends OptionCollector<Double>
{
	public OptionCollectorDouble(final String names)
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
