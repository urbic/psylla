package coneforest.cli;

public class OptionCollectorDouble extends OptionCollector<Double>
{
	public OptionCollectorDouble(String names)
	{
		super(names);
	}

	public Double parseArg(String arg)
		throws CLIProcessingException
	{
		double result;
		try
		{
			result=Double.parseDouble(arg);
		}
		catch(NumberFormatException e)
		{
			throw new CLIProcessingException("Bad option argument format: "+arg);
		}
		return result;
	}
}
