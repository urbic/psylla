package coneforest.psi;
import coneforest.psi.core.*;

/**
*	The Ψ interpreter launcher
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
			String consoleEncoding=java.nio.charset.Charset.defaultCharset().toString();
			final coneforest.cli.Processor cli=new coneforest.cli.Processor
				(
					new coneforest.cli.OptionFlag("help", "h", "?"),
					new coneforest.cli.OptionFlag("version", "V"),
					new coneforest.cli.OptionString("console-encoding", "C"),
					new coneforest.cli.OptionPath("classpath", "cp"),
					new coneforest.cli.OptionPath("librarypath", "lp", "I"),
					new coneforest.cli.OptionString("eval", "e"),
					new coneforest.cli.OptionString("locale", "L"),
					new coneforest.cli.OptionLong("random-seed", "S")
				);
			final int processed=cli.parse(args, 0);

			if(cli.getValue("console-encoding")!=null)
				consoleEncoding=cli.getValue("console-encoding");
			if(consoleEncoding!=null)
				try
				{
					System.setOut(new java.io.PrintStream(System.out, true, consoleEncoding));
					System.setErr(new java.io.PrintStream(System.err, true, consoleEncoding));
				}
				catch(java.io.UnsupportedEncodingException e)
				{
					System.err.println(Messages.format("unsupportedEncodingText", consoleEncoding));
					System.exit(1);
				}
			if(cli.getValue("locale")!=null)
				java.util.Locale.setDefault(java.util.Locale.forLanguageTag(cli.getValue("locale")));
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
							catch(PsiException e)
							{
							}
						}
					};

			// Configure standard writer and error writer
			interpreter.setWriter(new java.io.OutputStreamWriter(System.out));
			interpreter.setErrorWriter(new java.io.OutputStreamWriter(System.err));

			interpreter.acceptEnvironment(System.getenv());
			interpreter.acceptScriptName(scriptName);
			interpreter.acceptShellArguments(shellArguments);

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

			// Set random seed
			if(cli.getValue("random-seed")!=null)
				interpreter.dictStack().<PsiRandom>load("stdrandom")
					.psiSetSeed(PsiInteger.valueOf(cli.getValue("random-seed")));

			interpreter.start();
		}
		catch(PsiException e)
		{
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
		catch(coneforest.cli.CLIProcessingException e)
		{
			System.err.println(e.getLocalizedMessage());
			System.err.println(Messages.getString("useHelpOption"));
			System.exit(1);
		}
		catch(java.io.FileNotFoundException e)
		{
			System.out.println(Messages.format("scriptNotFoundText", e.getLocalizedMessage()));
			System.exit(1);
		}
	}

	private static Interpreter interpreter;

	public static void join()
		throws InterruptedException
	{
		interpreter.join();
	}

	public static void join(long millis)
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
