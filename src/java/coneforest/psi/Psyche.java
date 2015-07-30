package coneforest.psi;

public class Psyche
{
	public static void main(String args[])
	{
		String consoleEncoding=System.getProperty("consoleEncoding");

		int processed;
		try
		{
			messages=java.util.ResourceBundle.getBundle("coneforest.psi.Messages");

			try
			{
				cli=new coneforest.cli.Processor
					(
						new coneforest.cli.OptionFlag("help", "h", "?"),
						new coneforest.cli.OptionFlag("version", "V"),
						new coneforest.cli.OptionString("console-encoding", "C"),
						new coneforest.cli.OptionPath("classpath", "cp"),
						new coneforest.cli.OptionString("eval", "e"),
						new coneforest.cli.OptionString("locale", "L")
					);
			}
			catch(coneforest.cli.CLIConfigurationException e)
			{
				System.err.println(e.getLocalizedMessage());
				System.exit(1);
			}
			processed=cli.parse(args, 0);

			if(cli.getValue("console-encoding")!=null)
				consoleEncoding=((String)cli.getValue("console-encoding"));
			if(consoleEncoding!=null)
				try
				{
					System.setOut(new java.io.PrintStream(System.out, true, consoleEncoding));
					System.setErr(new java.io.PrintStream(System.err, true, consoleEncoding));
				}
				catch(java.io.UnsupportedEncodingException e)
				{
					System.err.println(String.format(messages.getString("unsupportedEncodingText"),
						consoleEncoding));
					System.exit(1);
				}
			if(cli.getValue("help"))
				help();
			if(cli.getValue("version"))
				version();
			if(cli.getValue("locale")!=null)
				java.util.Locale.setDefault(java.util.Locale.forLanguageTag((String)cli.getValue("locale")));


			final java.io.Reader scriptReader;
			final String scriptName;
			final String[] shellArguments;
			if(cli.getValue("eval")!=null)
			{
				scriptName="--eval";
				shellArguments=java.util.Arrays.copyOfRange(args, processed, args.length);
				scriptReader=new java.io.StringReader(cli.<String>getValue("eval"));
			}
			else if(processed<args.length)
			{
				scriptName=args[processed];
				shellArguments=java.util.Arrays.copyOfRange(args, processed+1, args.length);
				scriptReader=scriptName.equals("-")?
						new java.io.InputStreamReader(System.in):
						new java.io.FileReader(scriptName);
			}
			else
			{
				scriptName=null;
				scriptReader=null;
				shellArguments=null;
				// TODO REPL
			}
			Interpreter interpreter=new Interpreter()
				{
					@Override
					public void run()
					{
						interpret(scriptReader);
					}
				};
			interpreter.acceptEnvironment(System.getenv());
			interpreter.acceptScriptName(scriptName);
			interpreter.acceptShellArguments(shellArguments);
			//interpreter.setReader(new java.io.InputStreamReader(System.in));
			//interpreter.setWriter(new java.io.OutputStreamWriter(System.out));
			if(cli.getValue("classpath")!=null)
				interpreter.acceptClassPath(cli.<String[]>getValue("classpath"));

			interpreter.start();
		}
		catch(coneforest.cli.CLIProcessingException e)
		{
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
		catch(java.io.FileNotFoundException e)
		{
			System.out.println(String.format(messages.getString("scriptNotFoundText"),
					e.getLocalizedMessage()));
			System.exit(1);
		}
	}

	private static void help()
	{
		System.out.println(messages.getString("helpText"));
		System.exit(0);
	}

	private static void version()
	{
		System.out.print(String.format(messages.getString("versionText"),
				Version.getVersion()));
		System.exit(0);
	}

	private static java.util.ResourceBundle messages;
	private static coneforest.cli.Processor cli;
}
