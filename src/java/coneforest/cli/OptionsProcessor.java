package coneforest.cli;
import java.io.*;
import java.util.Locale;

/**
 * Options processor class
 */
public class OptionsProcessor
{
	/**
	 * Constructor	
	 * @param url URL of parser configuration document
	 */
	public OptionsProcessor(final Option... options)
		throws ConfigurationException
	{
		this.options=options;
	}

	public java.util.Iterator<Option> iterator()
	{
		return new java.util.Iterator<Option>()
			{
				public boolean hasNext()
				{
					return true;
				}

				public Option next()
				{
					return null;
				}

				public void remove()
				{
					throw new UnsupportedOperationException();
				}
							
				private int argIndex=0, argCharIndex=0;
				private boolean optionsProcessing=true;
			};
	}

	/*
	public void parse(final String[] args)
		throws ProcessingException
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
					int j=args[i].indexOf('=');
					if(j>=0)
					{
						String name=args[i].substring(2, j);
						String arg=args[i].substring(j+1);
						Option option=findOption(name);
						if(option instanceof OptionWithArg)
							((OptionWithArg)option).handle(arg);
						else
							throw new ProcessingException("Option --"+name+" does not need argument");
					}
					else
					{
						String name=args[i].substring(2);
						Option option=findOption(name);
						if(option instanceof OptionWithArg)
						{
							if(++i<args.length)
								((OptionWithArg)option).handle(args[i]);
							else
								throw new ProcessingException("Option --"+name+" needs argument");
						}
						else
							((OptionWithoutArg)option).handle();
					}
				}
				else if(args[i].startsWith("-"))
				{
					for(int j=1; j<args[i].length(); j++)
					{
						String name=args[i].substring(j, j+1);
						Option option=findOption(name);
						if(option instanceof OptionWithArg)
						{
							if(++j==args[i].length())
							{
								if(++i<args.length)
									((OptionWithArg)option).handle(args[i]);
								else
									throw new ProcessingException("Option -"+name+" needs argument");
							}
							else
							{
								((OptionWithArg)option).handle(args[i].substring(j));
								break;
							}
						}
						else
							((OptionWithoutArg)option).handle();
					}
				}
				else
					freeArgs.add(args[i]);
			}
			else
				freeArgs.add(args[i]);
		}
	}
	*/

	/*
	public Iterable<Option> parse(final String[] args)
	{
		return new Iterable<Option>()
			{
				public java.util.Iterator<Option> iterator()
				{
					return new java.util.Iterator<Option>()
						{
							public boolean hasNext()
							{
								return argIndex<args.length;
							}

							public Option next()
							{
								if(args[argIndex].equals("--"))
								{
									optionsProcessing=!optionsProcessing;
									argIndex++;
								}
								if(optionsProcessing)
								{
									return null;
								}
								else
								{
									try
									{
										freeArgs.handle(args[argIndex++]);
									}
									catch(ProcessingException e)
									{
										// TODO
									}
									return freeArgs;
								}
							}

							public void remove()
							{
								throw new UnsupportedOperationException();
							}

							private int argIndex=0, argCharIndex=0;
							private boolean optionsProcessing=true;
						};
				}

			};
	}
	*/


	public Option findOption(final String name)
		throws ProcessingException
	{
		for(Option option: options)
			if(option.hasName(name))
				return option;
		throw new ProcessingException("No definition for option: "+(name.length()==1? "-": "--")+name);
	}

	public Object getValue(final String name)
		throws ProcessingException
	{
		return findOption(name).getValue();
	}

	/*
	public String[] getFreeArgs()
	{
		return freeArgs.toArray(new String[freeArgs.size()]);
	}
	*/

	private final java.util.ArrayList<String> freeArgs=new java.util.ArrayList<String>();
	//private OptionCollectorString freeArgs=new OptionCollectorString("");
	private final Option[] options;
}
