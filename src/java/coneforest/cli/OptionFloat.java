package coneforest.cli;

public class OptionFloat
	extends OptionHolder<Float>
{
	public OptionFloat(String names)
	{
		super(names);
	}

	@Override
	public Float parseArg(String arg)
		throws CLIProcessingException
	{
		try
		{
			return Float.parseFloat(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException("Bad option argument format: "+arg);
		}
	}
}
