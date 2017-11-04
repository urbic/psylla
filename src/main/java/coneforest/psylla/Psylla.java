package coneforest.psylla;
import coneforest.psylla.core.*;

/**
*	The Ψ interpreter launcher.
*/
public class Psylla
{
	public static void main(final String args[])
	{
		try
		{
			launch(args);
		}
		catch(final PsyException e)
		{
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
		catch(final coneforest.cli.ProcessingException e)
		{
			System.err.println(e.getLocalizedMessage());
			System.err.println(Messages.getString("useHelpOption"));
			System.exit(1);
		}
		catch(final java.io.FileNotFoundException e)
		{
			System.out.println(Messages.format("scriptNotFoundText", e.getLocalizedMessage()));
			System.exit(1);
		}
	}

	public Psylla(final PsyllaConfig psyllaConfig)
		throws PsyException
	{
		interpreter=(psyllaConfig.scriptReader!=null)?
			new Interpreter()
				{
					@Override
					public void run()
					{
						interpret(psyllaConfig.scriptReader);
					}
				}:
			new Interpreter()
				{
					@Override
					public void run()
					{
						try
						{
							repl();
						}
						catch(final PsyException e)
						{
						}
					}
				};
		interpreter.setWriter(new java.io.OutputStreamWriter(System.out));
		interpreter.setErrorWriter(new java.io.OutputStreamWriter(System.err));
		interpreter.setEnvironment(System.getenv());
		interpreter.setShellArguments(psyllaConfig.shellArguments);
		interpreter.setScriptName(psyllaConfig.scriptName);
		interpreter.setRandomSeed(psyllaConfig.randomSeed);
		interpreter.setClassPath(psyllaConfig.classPath);
		interpreter.setLibraryPath(psyllaConfig.libraryPath);
	}

	/**
	*	Process command-line options and launches the Ψ interpreter.
	*
	*	@param args the command-line options
	*	@return the {@link Psylla} instance launched.
	*	@throws PsyException
	*	@throws coneforest.cli.ProcessingException
	*	@throws java.io.FileNotFoundException
	*/
	public static Psylla launch(final String args[])
		throws
			PsyException,
			coneforest.cli.ProcessingException,
			java.io.FileNotFoundException
	{

		final coneforest.cli.Processor cli=new coneforest.cli.Processor
			(
				new coneforest.cli.OptionFlag("help usage h ?"),
				new coneforest.cli.OptionFlag("version V"),
				new coneforest.cli.OptionString("console-encoding C"),
				new coneforest.cli.OptionPath("classpath cp"),
				new coneforest.cli.OptionPath("librarypath lp I"),
				new coneforest.cli.OptionString("eval e"),
				new coneforest.cli.OptionString("locale L"),
				new coneforest.cli.OptionLong("random-seed S")
			);
		final int processed=cli.parse(args, 0);

		Psylla.setConsoleEncoding(cli.getValue("console-encoding"));
		Psylla.setLocale(cli.getValue("locale"));
		if(cli.getValue("help"))
			help();
		if(cli.getValue("version"))
			version();

		final java.io.Reader scriptReader;
		final String scriptName;
		final String[] shellArguments;

		if(cli.getValue("eval")!=null)
		{
			scriptName="--eval";
			shellArguments=java.util.Arrays.copyOfRange(args, processed, args.length);
			scriptReader=new java.io.StringReader(cli.getValue("eval"));
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
			scriptName="--repl";
			scriptReader=null;
			shellArguments=new String[]{};
		}

		final PsyllaConfig psyllaConfig=new PsyllaConfig();
		psyllaConfig.setScriptName(scriptName);
		psyllaConfig.setScriptReader(scriptReader);
		psyllaConfig.setShellArguments(shellArguments);
		psyllaConfig.setRandomSeed(cli.getValue("random-seed"));
		psyllaConfig.setClassPath(cli.<String[]>getValue("classpath"));
		psyllaConfig.setLibraryPath(cli.<String[]>getValue("librarypath"));

		final Psylla psylla=new Psylla(psyllaConfig);
		psylla.start();
		return psylla;
	}

	public static void setConsoleEncoding(final String consoleEncoding)
	{
		String ce;
		if(consoleEncoding!=null)
			ce=consoleEncoding;
		else
			ce=java.nio.charset.Charset.defaultCharset().toString();
		try
		{
			System.setOut(new java.io.PrintStream(System.out, true, ce));
			System.setErr(new java.io.PrintStream(System.err, true, ce));
		}
		catch(final java.io.UnsupportedEncodingException e)
		{
			System.err.println(Messages.format("unsupportedEncodingText", consoleEncoding));
			System.exit(1);
		}
	}

	public static void setLocale(final String locale)
	{
		if(locale!=null)
			java.util.Locale.setDefault(java.util.Locale.forLanguageTag(locale));
	}

	public void start()
	{
		interpreter.start();
	}

	private final Interpreter interpreter;

	public void join()
		throws InterruptedException
	{
		interpreter.join();
	}

	public void join(long millis)
		throws InterruptedException
	{
		interpreter.join(millis);
	}

	private static void help()
	{
		System.out.println(Messages.getString("helpText"));
		System.exit(0);
	}

	private static void version()
	{
		System.out.print(Messages.format("versionText", Version.getVersion()));
		System.exit(0);
	}

	private static class PsyllaConfig
	{
		private void setScriptReader(final java.io.Reader scriptReader)
		{
			this.scriptReader=scriptReader;
		}

		private void setScriptName(final String scriptName)
		{
			this.scriptName=scriptName;
		}

		private void setShellArguments(final String[] shellArguments)
		{
			this.shellArguments=shellArguments;
		}

		private void setRandomSeed(final Long randomSeed)
		{
			this.randomSeed=randomSeed;
		}

		private void setClassPath(final String[] classPath)
		{
			this.classPath=classPath;
		}

		private void setLibraryPath(final String[] libraryPath)
		{
			this.libraryPath=libraryPath;
		}

		private java.io.Reader scriptReader;
		private String scriptName;
		private String[] shellArguments;
		private Long randomSeed;
		private String[] classPath, libraryPath;
	}
}
