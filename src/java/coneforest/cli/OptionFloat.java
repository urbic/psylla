package coneforest.cli;

public class OptionFloat extends OptionHolder<Float>
{
	public OptionFloat(String names)
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
		catch(NumberFormatException e)
		{
			throw new ProcessingException("Bad option argument format: "+arg);
		}
	}
}
