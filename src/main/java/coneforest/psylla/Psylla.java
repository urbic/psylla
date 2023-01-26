package coneforest.psylla;
import coneforest.psylla.core.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
*	The Psylla interpreter launcher.
*/
public class Psylla
{
	public static void main(final String args[])
	{
		try
		{
			launch(args);
		}
		catch(final PsyErrorException e)
		{
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
		catch(final coneforest.cli.ProcessingException ex)
		{
			System.err.println(ex.getLocalizedMessage());
			System.err.println(Messages.getString("useHelpOption"));
			System.exit(1);
		}
		catch(final FileNotFoundException ex)
		{
			System.out.println(Messages.format("badScript", ex.getLocalizedMessage()));
			System.exit(1);
		}
	}

	public Psylla(final PsyllaConfig psyllaConfig)
		throws PsyErrorException
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
						catch(final PsyErrorException e)
						{
							// NOP
						}
					}
				};
		interpreter.setWriter(new OutputStreamWriter(System.out));
		interpreter.setErrorWriter(new OutputStreamWriter(System.err));
		interpreter.setEnvironment(System.getenv());
		interpreter.setShellArguments(psyllaConfig.shellArguments);
		interpreter.setScriptName(psyllaConfig.scriptName);
		interpreter.setRandomSeed(psyllaConfig.randomSeed);
		interpreter.setClassPath(psyllaConfig.classPath);
		interpreter.setLibraryPath(psyllaConfig.libraryPath);
	}

	/**
	*	Process command-line options and launches the Psylla interpreter.
	*
	*	@param args the command-line options
	*	@return the {@link Psylla} instance launched.
	*	@throws PsyErrorException
	*	@throws coneforest.cli.ProcessingException
	*	@throws FileNotFoundException
	*/
	public static Psylla launch(final String args[])
		throws
			PsyErrorException,
			coneforest.cli.ProcessingException,
			FileNotFoundException
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
				new coneforest.cli.OptionLong("random-seed S"),
				new coneforest.cli.OptionCollectorString("config cfg")
			);
		final int processed=cli.parse(args, 0);

		Psylla.setConsoleEncoding(cli.getValue("console-encoding"));
		Psylla.setLocale(cli.getValue("locale"));
		if(cli.getValue("help"))
			showHelp();
		if(cli.getValue("version"))
			showVersion();
		if(!cli.<java.util.List<String>>getValue("config").isEmpty())
			showConfig(cli.getValue("config"));

		final Reader scriptReader;
		final String scriptName;
		final String[] shellArguments;

		if(cli.getValue("eval")!=null)
		{
			scriptName="--eval";
			shellArguments=java.util.Arrays.copyOfRange(args, processed, args.length);
			scriptReader=new StringReader(cli.getValue("eval"));
		}
		else if(processed<args.length)
		{
			scriptName=args[processed];
			shellArguments=java.util.Arrays.copyOfRange(args, processed+1, args.length);
			scriptReader=scriptName.equals("-")?
					new InputStreamReader(System.in):
					new FileReader(scriptName);
		}
		else
		{
			scriptName="--repl";
			shellArguments=new String[]{};
			scriptReader=null;
		}

		final var psyllaConfig=new PsyllaConfig();
		psyllaConfig.setScriptName(scriptName);
		psyllaConfig.setScriptReader(scriptReader);
		psyllaConfig.setShellArguments(shellArguments);
		psyllaConfig.setRandomSeed(cli.getValue("random-seed"));
		psyllaConfig.setClassPath(cli.<String[]>getValue("classpath"));
		psyllaConfig.setLibraryPath(cli.<String[]>getValue("librarypath"));

		final var psylla=new Psylla(psyllaConfig);
		psylla.start();
		return psylla;
	}

	public static void setConsoleEncoding(final String consoleEncoding)
	{
		final String ce=(consoleEncoding!=null)?
				consoleEncoding:
				Charset.defaultCharset().toString();
		try
		{
			System.setOut(new PrintStream(System.out, true, ce));
			System.setErr(new PrintStream(System.err, true, ce));
		}
		catch(final UnsupportedEncodingException ex)
		{
			System.err.println(Messages.format("unsupportedEncoding", consoleEncoding));
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

	private static void showHelp()
	{
		System.out.println(Messages.getString("help"));
		System.exit(0);
	}

	private static void showVersion()
	{
		System.out.print(Messages.format("version", Version.getVersion()));
		System.exit(0);
	}

	private static void showConfig(final java.util.List<String> patterns)
	{
		final java.util.Set<String> propertyNames=Config.stringPropertyNames();

		for(final String pattern: patterns)
		{
			propertyNames.stream()
					.sorted()
					.filter(name->Globs.matches(pattern, name))
					//.forEach(System.out::println);
					.forEach(name->System.out.println(name+"=\'"+Config.getProperty(name)+"\'"));
		}
		System.exit(0);
	}

	private static class PsyllaConfig
	{
		private void setScriptReader(final Reader scriptReader)
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

		private Reader scriptReader;
		private String scriptName;
		private String[] shellArguments;
		private Long randomSeed;
		private String[] classPath, libraryPath;
	}
}
