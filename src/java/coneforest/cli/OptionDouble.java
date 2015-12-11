package coneforest.cli;

public class OptionDouble
	extends OptionHolder<Double>
{
	public OptionDouble(String names)
	{
		super(names);
	}

	@Override
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
