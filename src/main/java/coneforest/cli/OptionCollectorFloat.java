package coneforest.cli;

public class OptionCollectorFloat extends OptionCollector<Float>
{
	public OptionCollectorFloat(final String names)
	{
		super(names);
	}

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
