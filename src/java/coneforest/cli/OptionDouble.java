package coneforest.cli;

public class OptionDouble extends OptionHolder<Double>
{
	public OptionDouble(String names)
	{
		super(names);
	}

	public Double parseArg(String arg)
		throws ProcessingException
	{
		double result;
		try
		{
			result=Double.parseDouble(arg);
		}
		catch(NumberFormatException e)
		{
			throw new ProcessingException("Bad option argument format: "+arg);
		}
		return result;
	}
}
