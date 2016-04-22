package coneforest.cli;
import java.io.*;
//import java.util.Locale;

/**
 * Options processor class
 */
public class Processor
{
	/**
	 * Constructor
	 *
	 * @param options a list of option descriptions
	 */
	public Processor(final Option... options)
	{
		this.options=options;
	}

	public int parse(final String[] args)
		throws CLIProcessingException
	{
		return parse(args, Integer.MAX_VALUE);
	}

	public int parse(final String[] args, final int freeArgsCount)
		throws CLIProcessingException
	{
		boolean optionsProcessing=true;

		for(int i=0; i<args.length; i++)
		{
			if(args[i].equals("--"))
			{
				optionsProcessing=!optionsProcessing;
				continue;
			}
			if(optionsProcessing)
			{
				if(args[i].startsWith("--"))
				{
					final int j=args[i].indexOf('=');
					if(j>=0)
					{
						final String name=args[i].substring(2, j);
						final String arg=args[i].substring(j+1);
						final Option option=findOption(name);
						if(option instanceof OptionWithArg)
							((OptionWithArg)option).handle(arg);
						else
							throw new CLIProcessingException(Messages.format("optProcExcpnDoesntNeedArg", "--"+name));
					}
					else
					{
						final String name=args[i].substring(2);
						final Option option=findOption(name);
						if(option instanceof OptionWithArg)
						{
							if(++i<args.length)
								((OptionWithArg)option).handle(args[i]);
							else
								throw new CLIProcessingException(Messages.format("optProcExcpnNeedsArg", "--"+name));
						}
						else
							((OptionWithoutArg)option).handle();
					}
				}
				else if(args[i].startsWith("-") && !args[i].equals("-"))
				{
					for(int j=1; j<args[i].length(); j++)
					{
						final String name=args[i].substring(j, j+1);
						final Option option=findOption(name);
						if(option instanceof OptionWithArg)
						{
							if(++j==args[i].length())
								if(++i<args.length)
									((OptionWithArg)option).handle(args[i]);
								else
									throw new CLIProcessingException(Messages.format("optProcExcpnNeedsArg", "-"+name));
							else
								((OptionWithArg)option).handle(args[i].substring(j));
							break;
						}
						else
							((OptionWithoutArg)option).handle();
					}
				}
				else
					if(freeArgs.size()<freeArgsCount)
						freeArgs.add(args[i]);
					else
						return i;
			}
			else
				if(freeArgs.size()<freeArgsCount)
					freeArgs.add(args[i]);
				else
					return i;
		}
		return args.length;
	}

	private Option findOption(final String name)
		throws CLIProcessingException
	{
		for(Option option: options)
			if(option.hasName(name))
				return option;
		throw new CLIProcessingException(Messages.format("optProcExcpnNoDefn", (name.length()==1? "-": "--")+name));
	}

	public <T> T getValue(final String name)
		throws CLIProcessingException
	{
		return findOption(name).getValue();
	}

	public String[] getFreeArgs()
	{
		return freeArgs.toArray(new String[freeArgs.size()]);
	}

	private final java.util.ArrayList<String> freeArgs=new java.util.ArrayList<String>();
	private final Option[] options;
}
