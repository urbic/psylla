package coneforest.psylla;

import coneforest.psylla.core.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
*	The Psylla interpreter launcher.
*/
public class Psylla
{
	public static void main(final String args[])
	{
		try
		{
			launch(System.out, System.err, args);
		}
		catch(final PsyErrorException e)
		{
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
		catch(final coneforest.clianthus.processor.ProcessingException ex)
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
		interpreter.setWriter(psyllaConfig.outputWriter);
		interpreter.setErrorWriter(psyllaConfig.errorWriter);
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
	*	@param args the command-line options.
	*	@return the {@link Psylla} instance launched.
	*	@throws PsyErrorException when TODO
	*	@throws coneforest.clianthus.processor.ProcessingException when TODO
	*	@throws FileNotFoundException when TODO
	*/
	public static Psylla launch(final PrintWriter outputWriter, final PrintWriter errorWriter,
			final String... args)
		throws
			PsyErrorException,
			coneforest.clianthus.processor.ProcessingException,
			FileNotFoundException
	{

		final var cli=new coneforest.clianthus.processor.Processor
			(
				new coneforest.clianthus.option.OptionFlag("help usage h ?"),
				new coneforest.clianthus.option.OptionFlag("version V"),
				new coneforest.clianthus.option.OptionString("console-encoding C"),
				new coneforest.clianthus.option.OptionPath("classpath cp"),
				new coneforest.clianthus.option.OptionPath("librarypath lp I"),
				new coneforest.clianthus.option.OptionString("eval e"),
				new coneforest.clianthus.option.OptionString("locale L"),
				new coneforest.clianthus.option.OptionLong("random-seed S"),
				new coneforest.clianthus.option.OptionCollectorString("config cfg")
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
			shellArguments=Arrays.copyOfRange(args, processed, args.length);
			scriptReader=new StringReader(cli.getValue("eval"));
		}
		else if(processed<args.length)
		{
			scriptName=args[processed];
			shellArguments=Arrays.copyOfRange(args, processed+1, args.length);
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
		psyllaConfig.setOutputWriter(outputWriter);
		psyllaConfig.setErrorWriter(errorWriter);
		psyllaConfig.setShellArguments(shellArguments);
		psyllaConfig.setRandomSeed(cli.getValue("random-seed"));
		psyllaConfig.setClassPath(cli.<String[]>getValue("classpath"));
		psyllaConfig.setLibraryPath(cli.<String[]>getValue("librarypath"));

		final var psylla=new Psylla(psyllaConfig);
		psylla.start();
		return psylla;
	}

	public static Psylla launch(
			final PrintStream outputStream, final PrintStream errorStream,
			final String... args)
		throws
			PsyErrorException,
			coneforest.clianthus.processor.ProcessingException,
			FileNotFoundException
	{
		return launch(new PrintWriter(outputStream), new PrintWriter(errorStream), args);
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

	private static void showConfig(final List<String> patterns)
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

		private void setOutputWriter(final PrintWriter outputWriter)
		{
			this.outputWriter=outputWriter;
		}

		private void setErrorWriter(final PrintWriter errorWriter)
		{
			this.errorWriter=errorWriter;
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
		private PrintWriter outputWriter;
		private PrintWriter errorWriter;
		private String[] shellArguments;
		private Long randomSeed;
		private String[] classPath, libraryPath;
	}
}
