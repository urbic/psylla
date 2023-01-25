package coneforest.cli;

/**
*	An option holding a {@link Float} value.
*/
public class OptionFloat
	extends OptionHolder<Float>
{
	/**
	*	Creates new option with given names.
	*
	*	@param names space-delimited names.
	*/
	public OptionFloat(final String names)
	{
		super(names);
	}

	@Override
	public Float parseArg(final String arg)
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
