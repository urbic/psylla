package coneforest.psi;
import coneforest.psi.core.*;

/**
*	The Ψ interpreter launcher.
*/
public class Psylla
{
	/**
	*	Process comman-line options and launches the Ψ interpreter.
	*
	*	@param args the command-line options
	*/
	public static void main(final String args[])
	{
		try
		{
			launch(args);
		}
		catch(final PsiException e)
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

	public static Psylla launch(final String args[])
		throws
			PsiException,
			coneforest.cli.ProcessingException,
			java.io.FileNotFoundException
	{
		final Psylla psylla=new Psylla();

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

		psylla.setScript(scriptReader, scriptName, shellArguments);

		/*
		// Configure class path
		final PsiArraylike<PsiStringy> oClassPath
			=(PsiArraylike<PsiStringy>)interpreter.systemDict().get("classpath");
		final String envClassPath=System.getenv("PSYLLA_CLASSPATH");
		if(envClassPath!=null)
			for(String pathItem: envClassPath.split(java.io.File.pathSeparator))
				oClassPath.psiAppend(new PsiName(pathItem));
		if(cli.getValue("classpath")!=null)
			for(String pathItem: cli.<String[]>getValue("classpath"))
				oClassPath.psiAppend(new PsiName(pathItem));

		// Configure library path
		final PsiArraylike<PsiStringy> oLibraryPath
			=(PsiArraylike<PsiStringy>)interpreter.systemDict().get("librarypath");
		final String envLibraryPath=System.getenv("PSYLLA_LIB");
		if(envLibraryPath!=null)
			for(String pathItem: envLibraryPath.split(java.io.File.pathSeparator))
				oLibraryPath.psiAppend(new PsiName(pathItem));
		if(cli.getValue("librarypath")!=null)
			for(String pathItem: cli.<String[]>getValue("librarypath"))
				oLibraryPath.psiAppend(new PsiName(pathItem));
		*/

		psylla.setRandomSeed(cli.getValue("random-seed"));
		psylla.start();
		return psylla;
	}

	public static void setConsoleEncoding(final String consoleEncoding)
	{
		String ce=java.nio.charset.Charset.defaultCharset().toString();
		if(consoleEncoding!=null)
			ce=consoleEncoding;
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

	public void setScript(final java.io.Reader scriptReader, final String scriptName, final String[] shellArguments)
		throws PsiException
	{
		interpreter=(scriptReader!=null)?
			new Interpreter()
				{
					@Override
					public void run()
					{
						interpret(scriptReader);
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
						catch(final PsiException e)
						{
						}
					}
				};
		interpreter.setWriter(new java.io.OutputStreamWriter(System.out));
		interpreter.setErrorWriter(new java.io.OutputStreamWriter(System.err));
		interpreter.setEnvironment(System.getenv());
		interpreter.setShellArguments(shellArguments);
		interpreter.setScriptName(scriptName);
	}

	public void setRandomSeed(final Long randomSeed)
		throws PsiException
	{
		if(randomSeed!=null)
			((PsiRandom)interpreter.systemDict().get("stdrandom"))
				.psiSetSeed(PsiInteger.valueOf(randomSeed));
	}

	public void start()
	{
		interpreter.start();
	}

	private Interpreter interpreter;

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
}
