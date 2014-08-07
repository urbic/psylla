package coneforest.cli;

public class OptionCollectorFloat extends OptionCollector<Float>
{
	public OptionCollectorFloat(String names)
	{
		super(names);
	}

	public Float parseArg(String arg)
		throws ProcessingException
	{
		float result;
		try
		{
			result=Float.parseFloat(arg);
		}
		catch(NumberFormatException e)
		{
			throw new ProcessingException("Bad option argument format: "+arg);
		}
		return result;
	}
}
