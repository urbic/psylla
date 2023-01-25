package coneforest.cli;

/**
*	An option holding a list of {@link Float} values.
*/
public class OptionCollectorFloat extends OptionCollector<Float>
{
	/**
	*	Creates new option with given names.
	*
	*	@param names space-delimited names.
	*/
	public OptionCollectorFloat(final String names)
	{
		super(names);
	}

	@Override
	public Float parseArg(String arg)
		throws ProcessingException
	{
		try
		{
			return Float.parseFloat(arg);
		}
		catch(final NumberFormatException e)
		{
			throw new ProcessingException(Messages.format("optProcExcpnBadArg", arg));
		}
	}
}
