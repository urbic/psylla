package coneforest.cli;

public class OptionCollectorFloat extends OptionCollector<Float>
{
	public OptionCollectorFloat(String names)
	{
		super(names);
	}

	public Float parseArg(String arg)
		throws CLIProcessingException
	{
		float result;
		try
		{
			result=Float.parseFloat(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException("Bad option argument format: "+arg);
		}
		return result;
	}
}
