package coneforest.cli;

public class OptionCollectorLong extends OptionCollector<Long>
{
	public OptionCollectorLong(String names)
	{
		super(names);
	}

	public Long parseArg(String arg)
		throws ProcessingException
	{
		long result;
		try
		{
			result=Long.parseLong(arg);
		}
		catch(NumberFormatException e)
		{
			throw new ProcessingException("Bad option argument format: "+arg);
		}
		return result;
	}
}
