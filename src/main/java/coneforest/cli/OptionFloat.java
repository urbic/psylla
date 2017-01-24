package coneforest.cli;

public class OptionFloat
	extends OptionHolder<Float>
{
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
